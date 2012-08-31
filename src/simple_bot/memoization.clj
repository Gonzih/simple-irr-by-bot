(ns simple-bot.memoization
  (:require [simple-bot.fetch :as fetch]
            [simple-bot.notify :as notify]))

(def last-item (atom nil))

(defn update-last [item]
  (reset! last-item (:hash item)))

(defn get-new
  ([data]
    (get-new data []))
  ([data coll]
    (let [item (first data)
          result (conj coll item)]
      (if (= @last-item (:hash item))
        result
        (recur (rest data) result)))))

(defn check-for-new []
  (if (nil? @last-item)
    (let [item (first (fetch/results))]
      (update-last item)
      (notify/item item))
    (let [data (fetch/results)
          new-items (get-new data)
          new-item (first new-items)]
      (when (> (count new-items) 0)
        (update-last new-item)
        (notify/items new-items)))))
