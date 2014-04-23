(ns natural-deduction.core)

; all
(def all-e {:name "∀x e"
            :pre '[(variable $1) (all $2 (function $3 $2))]
            :post '[(function $3 $1)]
            :dir :forward})

(def all-i {:name "∀x i"
            :pre ...
            :post ...
            :dir :backward})

; exists
(def exists-e {:name "∃x e"
            :pre ...
            :post ...
            :dir :forward})

(def exists-i {:name "∃x i"
            :pre ...
            :post ...
            :dir :backward})
