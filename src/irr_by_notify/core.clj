(ns irr-by-notify.core
  (:require [irr-by-notify.memoization :as memo]))

(defn -main [& args]
  (memo/check-for-new))
