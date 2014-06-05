(ns natural-deduction.core)

(defn substitution
  [predicate-formula new-var]
  (let [[pre _ var pred] predicate-formula]
    (when (= pre 'predicate-formula)
      (if (contains? (set (flatten pred)) new-var)
        (throw (IllegalArgumentException. (str "The variable \"" new-var "\" that shall be inserted already exists in \"" pred"\".")))
        (clojure.walk/prewalk-replace {var new-var} pred)))))

(defn- reform
  [form quoted?]
  (cond
    (= \$ (first (str form))) (str (when quoted? "~") form)
    (coll? form) (str (when (not quoted?) "`")"(" (apply str (interpose " " (map #(reform % true) form))) ")")
    :else (str (when quoted? "~") "'" form)))

(defn- apply-rule
  [rule]
  (let [args (:args rule)
        forms (:forms rule)
        vars (vec (set (filter
                         #(and
                            (= \$ (first (str %)))
                            (not (contains? (set args) %)))
                         (flatten forms))))]
    (str "(fn "
         args " "
         "(fresh " vars " "
         (apply str (map #(str "(clojure.core.logic/== " (reform (first %) false) " " (reform (second %) false) ")") forms))
         "))")))

(defn- apply-rule-1step
 [foreward? rule terms]
 (let [movement (if foreward? (:foreward rule) (:backward rule))]
   (when (and
           movement
           (= (inc (count terms)) (count (:args rule))))
     (let [index (atom -1)
           r (apply-rule rule)
           args (apply str (interpose " " (map
                                            (fn [x]
                                              (if (= x movement)
                                              'q
                                              (str "'" (nth terms (swap! index inc)))))
                                            (:args rule))))
           function (str "(run 1 [q] (" r " " args "))")
           res (first (eval (read-string function)))]
       (postwalk
         (fn [x]
           (if (and (coll? x) (= (first x) 'substitution))
             (let [[_ form var] x]
               (substitution form var))
             x))
         res)))))

(defn apply-rule-foreward
  [rule & terms]
  (apply-rule-1step true rule terms))

(defn apply-rule-backward
  [rule & terms]
  (apply-rule-1step false rule terms))
