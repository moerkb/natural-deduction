(ns natural-deduction.core)

(defn pre-top?
  "True if the argument is a map and has the key :pre-top."
  [rule-map]
  (and (map? rule-map) (contains? rule-map :pre-top)))

(defn pre-bot?
  "True if the argument is a map and has the key :pre-bot."
  [rule-map]
  (and (map? rule-map) (contains? rule-map :pre-bot)))

(defn subs?
  "True if the argument is a map and has the key :subs."
  [rule-map]
  (and (map? rule-map) (contains? rule-map :subs)))