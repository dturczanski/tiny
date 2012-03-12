(ns tiny.app
  (:use tiny.routes
        ring.middleware.session
        [hiccup.middleware :only (wrap-base-url)]
        [ring.adapter.jetty :only (run-jetty)])
  (:require
   [compojure.handler :as handler]))

(def app
  (-> (handler/site #'main-routes)
    (wrap-session)
    (wrap-base-url)))

(defn run [] (run-jetty (var app) {:port 8080 :join? false}))

(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (run-jetty app {:port port})))