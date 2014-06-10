(ns natural-deduction.core)

(defn scope-from
  "Takes a world and an element in this world.
   The world is a nested vector like [a [b c] d].
   The result is a set of the scope of the element in this world.
   e.g. (scope-from [a [b c] d] a) => #{a d}"
  ([world elem]
  (scope-from world [] elem false))
  
  ([world scope elem inner?]
  (let [new-scope (if inner?
                    scope
                    (conj scope world))
        f (first world)
        n (next world)]
    (if f
      (if (vector? f)
        (let [s (scope-from (vec f) new-scope elem false)]
          (if s
            s
            (if n
              (scope-from (vec n) new-scope elem true)
              nil)))
        (if (= f elem)
          (set (flatten (map #(filter (complement vector?) %) new-scope)))
          (scope-from (vec n) new-scope elem true)))
    nil))))
