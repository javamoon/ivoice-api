(ns ivoice-api.core
  (:require [ring.middleware.cookies :refer :all]
      			[ring.middleware.params :refer :all]
      			[ring.middleware.keyword-params :refer :all]
            [ring.middleware.multipart-params :refer :all]
      			[ring.util.response :refer :all]
      			[compojure.core :refer :all]
            [compojure.route :as route]
      			[clojure.data.json :as json]
      			[clojure.java.io :as io]
            [ring.middleware.file :refer :all]
            [ring.middleware.resource :refer :all]
      			[ring.adapter.jetty :refer :all]
            [ivoice-api.msgsend :refer :all]
            [ivoice-api.selmer :refer :all]
            [ivoice-api.handler-demo :refer :all]
            [ivoice-api.handler :refer :all]
            [ivoice-api.middleware :refer :all])
  (:gen-class))

(defroutes app-routes
  (GET "/" [] "Ivoice Server")

  ;; selmer demo
  (ANY "/html" [] 
       selmer-demo-handler)

  (ANY "/demo" [] "Ivoice Demo")

  (context "/ivoice/resource" []
    (ANY "/:type/:location/:target" 
         [type location target]
         (resource-handler type location target) ))

  (context "/ivoice/api" []
    (ANY "/hotspot" [] (-> hostpot-handler 
                           wrap-echo-json
              					   wrap-cookies
              					   wrap-keyword-params
              					   wrap-params))
    (ANY "/latest" [] (str "latest"))
    (ANY "/play" [] 
         (-> play-handler
             wrap-echo-json
             wrap-keyword-params
             wrap-params)))

  (context "/ivoice/api/album" []
    (ANY "/" [] (str "hello demo"))
    (ANY "/save" [] 
         (-> album-save-handler
             wrap-echo-json))
    (ANY "/privylist" [] 
         (-> album-privylist-handler 
             wrap-echo-json)))

  (context "/ivoice/api/resource" []
    (POST "/uploadPage" [] (-> uploadPage-handler
                               wrap-echo-json
                               wrap-keyword-params
                               wrap-multipart-params))

    (ANY "/load" [] (-> load-handler 
                        wrap-echo-json))

    (ANY "/edit" [] (-> edit-handler 
                        wrap-echo-json
                        wrap-keyword-params
                        wrap-params))

    (ANY "/save" [] (-> save-handler 
                        wrap-echo-json))

    (POST "/upload" [] (-> upload-handler 
                           wrap-echo-json
                           wrap-keyword-params
                           wrap-multipart-params))

    (ANY "/download" [] (response "")))

  (route/not-found "404: Not Found"))

(def app app-routes)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (run-jetty app {:port 3001}))
