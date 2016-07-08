(ns ivoice-api.handler
  (:require [ring.util.response :refer :all]
      	  	[clojure.data.json :as json]
      	  	[clojure.java.io :as io]
      	  	[ivoice-api.util :refer :all]
      	  	[ivoice-api.service :refer :all]
            [ivoice-api.resource :refer :all]))

(defn upload-handler [req]
  (upload-util-service 
   req 
   (fn [x] 
     (let [contentId (save-content-service x)] 
  	   (assoc-in response-body [:data :contentId] contentId)))))

(defn uploadPage-handler [req]
  (upload-util-service
   req
   (fn [x]
     (let [resourceId (save-resource-service x)]
	   (assoc-in response-body [:data :resourceId] resourceId)))))

(defn resource-handler 
  [type location target]
  (storage-response type location target))

(defn edit-handler
  [req]
  (-> (if-let [resp (edit-resource-service req)] 
        resp
        response-error)
      response-json))
