(ns natural-deduction.core)

; all
(def all-e {:name "∀x e"
            :pre '[(variable $1) (all $2 (predicate $3 $2))]
            :post '[(predicate $3 $1)]
            :dir :forward})

(def all-i {:name "∀x i"
            :pre '[[(variable $1) (predicate $2 $1)]]
            :post '[(all $3 (predicate $2 $3))]
            :dir :backward})

; exists
(def exists-e {:name "∃x e"
            :pre '[(exists $1 (predicate $2 $1)) [[(variabl $3) (predicate $2 $3)] $4]]
            :post '[$4]
            :dir :forward})

(def exists-i {:name "∃x i"
            :pre '[(predicate $1 $2)]
            :post '[(exists $3 (predicate $1 $3))]
            :dir :backward})
