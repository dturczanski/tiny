(ns tiny.controllers
  (:use 
    tiny.views  
    tiny.core)
  (:require 
   [clojure.string :as str]
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