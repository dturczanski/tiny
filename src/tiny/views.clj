(ns tiny.views
  (:use [hiccup core page-helpers form-helpers]))

(defn include-all-js "Include all necessary javascript files" []
  (html
   (include-js "https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js")
   (include-js "https://raw.github.com/malsup/form/22e9c82ee47b5c0e245e7e32773697343f8d8a6e/jquery.form.js")
   (include-js "js/app.js")))

(defn render-form "Renders the main form" []
     (html 
       (form-to {:id "urlForm"} [:post "makeURL"]
                (label "url" "http://")
                (text-field "url")
                (submit-button "Go"))))

(defn index-page "Renders the whole main page from parts" []
     (html5 
       [:head
        [:title "Shorthand Site"]
        (include-all-js)
        (include-css "css/style.css")]
       [:body
        [:div {:id "content-block"}
         (render-form)
         [:div {:id "htmlResult"} "your link goes here" ]]]))

(defn render-link
  "Renders a resulting clickable link"
  [url referer]
  (html5 
    [:a {:href url} (str referer url)]))