(ns clojure-club.x121-universal-computation-engine.mbastian)

(defn compute [[op & r]]
  (let [ops {'+ + '- - '/ / '* * 'sin #(Math/sin %) 'cos #(Math/cos %) '** #(Math/pow %1 %2)}]
    (fn [m] (apply
              (ops op)
              (map (fn [t]
                     (cond
                       (symbol? t) (m t)
                       (number? t) t
                       :default ((compute t) m))) r)))))

;; -----------------------------------------------------------------------------

(assert (= 2 ((compute '(/ a b))
               '{b 8 a 16})))

(assert (= 8 ((compute '(+ a b 2))
               '{a 2 b 4})))

(assert (= [6 0 -4]
           (map (compute '(* (+ 2 a)
                          (- 10 b)))
                '[{a 1 b 8}
                  {b 5 a -2}
                  {a 2 b 11}])))

(assert (= 1 ((compute '(/ (+ x 2)
                        (* 3 (+ y 1))))
               '{x 4 y 1})))

((compute '(+ (** (sin (+ x 2)) 2) (** (cos (+ x 2)) 2))) '{x 1})
