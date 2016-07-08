(ns ivoice-api.service
  (:require [ivoice-api.korma :refer :all]
      			[ivoice-api.util :refer :all]
            [ivoice-api.extract :refer :all]
            [ivoice-api.upload :refer :all]
            [ivoice-api.cfg :refer [COVER-DEFAULT]]
      			[clojure.string :as stx]))

;; default user ID -> for demo
(def UID 1)

(defn upload-util-service [req callback] 
  (-> (if-let [m (upload-extract req)]
        (let [fm (upload m)]
          (callback (merge m fm)))
        response-error)
      response-json))

(defn save-content-service
  "<contentId:int>"
  [{:keys [content-type size filename type-value target location]}]
  (let [postfix (if-let [index (stx/last-index-of filename ".")]
  				  (subs filename (inc index)) "")
		generated (content-insert {:length size
			  					   :location location
			  					   :target target
			  					   :postfix postfix
			  					   :mime content-type
			  					   :type type-value
			  					   :status 1
			  					   :ucode (uuid)
			  					   :created (now)})]
    (:generated_key generated) ) )

(defn save-resource-service
  "<resourceId:int>"
  [m]
  (let [contentId (save-content-service m)
  	    generated (resource-insert {:media_id contentId
  	    							:uid UID 
  	    							:alaya 0
  	    							:visits 0
  	    							:favor 0
  	    							:download 0
  	    							:status 1
  	    							:scores 0
  	    							:group 0
  	    							:ucode (uuid)
  	    							:created (now)})]
  	(:generated_key generated) ) )

(defn edit-resource-service
  "in: <req>
   out: {}"
  [{{id :id} :params}]
  (when-let [id-value (parse-int id)]
    (let [resp (resource-edit id)]
      (if (empty? resp)
        response-empty
        (->> (let [assemble-cover (fn [{cover :cover_id :as r}]
                                   (->> (if cover
                                          (let [root "/ivoice/resource/"
                                                {:keys [postfix location target]} r] 
                                                (str root "/" postfix "/" location "/" target))
                                           COVER-DEFAULT)
                                        (assoc r :cover_address)))]
              (-> (first resp) 
                  assemble-cover
                  (update :created #(format-timestamp %))))
            (assoc response-body :data))
            ))))
