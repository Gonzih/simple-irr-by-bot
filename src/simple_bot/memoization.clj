(ns simple-bot.memoization
  (:use     [clojure.tools.logging :only (info error)])
  (:require [simple-bot.fetch :as fetch]
            [simple-bot.notify :as notify]))

(def old-items (atom #{}))

(defn add-to-old [items]
  (doall (map #(swap! old-items conj (:hash %)) items)))

(defn get-new [data]
  (filter #(not (contains? @old-items (:hash %))) data))

(defn check-for-new []
  (info "Checking...")
  (let [data (fetch/results)
        new-items (get-new data)]
    (info "Found " (count new-items) " new items")
    (add-to-old new-items)
    (when (> (count new-items) 0)
      (notify/items new-items))))
