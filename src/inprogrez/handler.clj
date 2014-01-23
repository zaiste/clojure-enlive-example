(ns inprogrez.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [tentacles.issues :as ti]
            [inprogrez.templates :as tpl]))

(def projects ["stormplan-ios" "nukomeet.com" "coworfing"])

(defn milestones [repo]
  (ti/repo-milestones "nukomeet" repo {:oauth-token "f7bc3d57e420885f8ea9d7d4cba8e62044b1525b"}))

(defn milestones-for-project [project]
  (map #(select-keys % [:number :title :due_on]) (milestones project)))

(def milestones-per-project
  (zipmap projects (map #(milestones-for-project %) projects)))

(defroutes app-routes
  (GET "/" [] (tpl/index milestones-per-project "stormplan-ios"))
  (GET "/:id" [id] (tpl/index milestones-per-project id))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
