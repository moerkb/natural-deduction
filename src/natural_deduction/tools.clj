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

(defn forward?
  "True if the rule can be applied forwards."
  [rule]
  (and
    (map? rule)
    (contains? rule :dir)
    (or 
      (= :forward (:dir rule))
      (= :both (:dir rule)))))

(defn backward?
  "True if the rule can be applied backwards."
  [rule]
  (and
    (map? rule)
    (contains? rule :dir)
    (or 
      (= :backward (:dir rule))
      (= :both (:dir rule)))))

(defn both-dir?
  "True if the rule can be applied forwards and backwards."
  [rule]
  (and 
    (forward? rule)
    (backward? rule)))