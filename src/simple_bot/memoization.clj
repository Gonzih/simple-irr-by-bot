(ns simple-bot.memoization
  (:use     [clojure.tools.logging :only (info error)])
  (:require [simple-bot.fetch :as fetch]
            [simple-bot.notify :as notify]))

(def last-item (atom nil))

(defn update-last [item]
  (reset! last-item (:hash item)))

(defn get-new
  ([data]
    (get-new data []))
  ([data coll]
    (let [item (first data)]
      (if (= @last-item (:hash item))
        coll
        (let [result (conj coll item)]
          (recur (rest data) result))))))

(defn check-for-new []
  (info "Checking...")
  (if (nil? @last-item)
    (let [item (first (fetch/results))]
      (info "last-item is nil")
      (update-last item)
      (notify/items [item]))
    (let [data (fetch/results)
          new-items (get-new data)
          new-item (first new-items)]
      (info "last-item is not nil")
      (when (> (count new-items) 0)
        (info "Found " (count new-items) " new items")
        (update-last new-item)
        (notify/items new-items)))))
