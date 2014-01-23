(ns inprogrez.templates
  (:require [net.cgrand.enlive-html :as enlive]
            [clj-time.local :as local]))

(defn to-date [date-str]
  (local/format-local-time (local/to-local-date-time date-str) :date))

(enlive/defsnippet show-milestone "public/index.html" [:#milestones :li]
  [{due_on :due_on title :title}]
  [:.due_on] (enlive/content (to-date due_on))
  [:.title] (enlive/content title))

(enlive/defsnippet show-project "public/index.html" [:#projects :li]
  [name]
  [:a] (enlive/content name)
  [:a] (enlive/set-attr :href name))

(enlive/deftemplate index "public/index.html"
  [milestones-per-project project]
  [:#projects] (enlive/content (map show-project (keys milestones-per-project)))
  [:#milestones] (enlive/content (map show-milestone (milestones-per-project project))))
