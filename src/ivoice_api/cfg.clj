(ns ivoice-api.cfg)

(def cfg {:host "",
	      :dbcfg {:db "", 
	   	          :host "", 
		          :port 3306, 
		          :user "", 
		          :password ""}})

;(def STORAGE-PATH "/home/dev/storage/")
 (def STORAGE-PATH "")

(def RESOURCE "RESOURCE/")
(def IMAGE "IMAGE/")
(def MULTIPLE "MULTIPLE/")

(def RESOURCE-SET #{201 202 301})
(def IMAGE-SET #{401 402})

(def COVER-DEFAULT "")
