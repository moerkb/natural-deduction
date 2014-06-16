(ns natural-deduction.core-test)

(deftest scope-from-test
  (let [a {:body 'a, :hash 1, :rule :premise}
        b {:body 'b, :hash 2, :rule :premise}
        c {:body 'c, :hash 3, :rule :premise}
        d {:body 'd, :hash 4, :rule :premise}
        e {:body 'e, :hash 5, :rule :premise}
        f {:body 'f, :hash 6, :rule :premise}
        world [a [b [c] d] [e] f]]
    
    (testing "Scope"
             (is (= #{a f} (scope-from world a)))
             (is (= #{a b d f} (scope-from world b)))
             (is (= #{a b c d f} (scope-from world c)))
             (is (= #{a b d f} (scope-from world d)))
             (is (= #{a e f} (scope-from world e)))
             (is (= #{a f} (scope-from world f))))))
