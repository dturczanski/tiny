(ns tiny.routes
  (:use 
    compojure.core
    tiny.controllers
    tiny.views)
  (:require
    [compojure.route :as route]))

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
