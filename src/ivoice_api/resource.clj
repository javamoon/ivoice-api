(ns ivoice-api.resource
  (:require [ring.util.response :refer :all]
            [ivoice-api.util :refer :all]
  	        [ivoice-api.cfg :refer :all]))

(def ROOT-OPTS {:root STORAGE-PATH 
				:allow-symlinks? true})

(def resource-path-set #{"mp4" "aac" "wav"})

(def image-path-set #{"jpg" "png"})

(def mime-types 
  {"mp4" "video/mp4"
   "aac" "audio/aac"
   "wav" "audio/wav"
   "jpg" "image/jpeg"
   "png" "image/png"})

(defn require-path 
  [type location target]
  (let [path-dir (cond (resource-path-set type) RESOURCE
  					    	(image-path-set type) IMAGE)]
  	(str path-dir location "/" target) ) )

(defn storage-response 
  [type location target]
  (when-let [mime-type (mime-types type)]
  	(let [path (require-path type location target)]
      (prn path)
      (prn ROOT-OPTS)
      (if-let [resp (file-response path ROOT-OPTS)]
        (-> resp
            (content-type mime-type) )
        (-> response-error
            to-json
            response) ) ) ) )
