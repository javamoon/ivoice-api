(ns ivoice-api.korma
  (:require [korma.db :refer :all]
            [korma.core :as db :refer [defentity table database insert select values 
            						   where set-fields has-many many-to-many belongs-to 
            						   with fields]]
            [ivoice-api.cfg :refer :all])
  (:import [java.util UUID Date]))

(defdb dbcfg
  (mysql (:dbcfg cfg)))


(declare users resource content media cover users2 album)

(defentity users (has-many resource {:fk :uid})
				 (many-to-many users2 :users_follow
				 			   {:lfk :uid :rfk :follow_id}))

(defentity users2 (table :users :users2)
				  (many-to-many users :users_follow ))

(defentity content)

(defentity media (table :content :media)
				 (has-many resource))

(defentity cover (table :content :cover) 
				 (has-many resource))

(defentity resource 
  (belongs-to users {:fk :uid})
  (belongs-to media {:fk :media_id}) 
  (belongs-to cover {:fk :cover_id})
  (belongs-to album {:fk :album_id}))

(defentity album)

(defn content-insert 
  "in: [m:{}]
   out: {:generated_key <id:int>}"
  [m]
  (insert content (values m)))

(defn resource-insert 
  "in: [m:{}]
   out: {:generated_key <id:int>}"
  [m]
  (insert resource
  		  (values m)))

(defn resource-update
  "in: [<id:int> <m:{}>]
   out: <updated-count:int>"
  [id m]
  (db/update resource
		     (set-fields m)
		     (where {:id id})))

(defn resource-edit 
  [id]
  (select resource 
          (with cover)
          (with album)
          (fields :id 
                  :title
                  :description
                  :cover_id
                  :album_id
                  [:cover.type :cover-type] 
                  [:album.title :album_name]
                  :cover.mime
                  :cover.postfix
                  :cover.location
                  :cover.target
                  :created)
          (where {:id id})))

