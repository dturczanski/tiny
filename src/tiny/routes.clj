(ns tiny.routes
  (:use compojure.core
        ring.middleware.session
        tiny.views
        tiny.core
        [hiccup.middleware :only (wrap-base-url)]
        [ring.adapter.jetty :only (run-jetty)])
  (:require
    [clojure.string :as str]
    [compojure.route :as route]
    [compojure.handler :as handler]
    [compojure.response :as resp]
    [ring.util.response :as ringresp]))


(defn shorten-url [session url referer]
  (let [counter (if (integer? (session :counter)) (inc (session :counter)) 1000)
        new-url (num->base62 counter)
        urls (session :urls)]
    {:headers {"Content-type" "text/html"}
     :body (if-not (str/blank? url) (render-link new-url referer))
     :session {:urls (if (map? urls)
                       (assoc urls new-url url)
                       {new-url url})
               :counter counter}}))

(defn tiny-redirect [urls shorthand]
  (let [url-to (str "http://" (urls shorthand))]
    (ringresp/redirect url-to)))

(defroutes main-routes
  (GET "/" 
       [] 
       (index-page))
  (POST "/makeURL" 
        {session :session params :params headers :headers} 
        (shorten-url session (params :url) (headers "referer")))
  (GET ["/:shorthand", :shorthand #"[0-9a-zA-z]+"] 
       {session :session params :params} 
       (tiny-redirect (session :urls) (params :shorthand))) 
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site #'main-routes)
    (wrap-session)
    (wrap-base-url)))

(defn run [] (run-jetty (var app) {:port 8080 :join? false}))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))