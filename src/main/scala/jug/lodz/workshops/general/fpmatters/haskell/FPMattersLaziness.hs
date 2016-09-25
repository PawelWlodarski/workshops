next :: Double->Double->Double
next n x = (x+n/x)/2

within :: Double -> [Double] -> Double
within eps (a:b:rest) = if abs (a-b) < eps
                        then b
                        else within eps (b:rest)

relative :: Double -> [Double] -> Double
relative eps (a:b:rest) = if abs (a/b-1) < eps
                          then b
                          else within eps (b:rest)

squareRoot ::Double ->Double ->Double
squareRoot eps n = within eps (iterate (next n) 1.0)

squareRoot2 ::Double ->Double ->Double
squareRoot2 eps n = relative eps (iterate (next n) 1.0)


-- DEMO infinite lists
-- try [1..]
-- *> take 10 $ [1..]
-- [1,2,3,4,5,6,7,8,9,10]
-- *> take 10 $ map (+1) [1..]
-- [2,3,4,5,6,7,8,9,10,11]
-- *> take 10 $ filter (\x -> x `mod` 2 == 0 ) [1..]
-- [2,4,6,8,10,12,14,16,18,20]
-- *> foldr (+) 0 $ take 10 [1..]
-- 55



-- EXERCISE
-- fizzBuzz :: [String]
-- fizzBuzz = map changeFizzbuzz [1..]
--   where
--     changeFizzbuzz i
--       | i `mod` 3 == 0 && i `mod` 5==0 = ???
--       | ???
--       | ???
--       | otherwise = ???