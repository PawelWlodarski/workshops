-- DEMO 1 - REPL COMPOSITION
-- > let f::Int->Int;  f i  =  i + 1
-- > :t f
-- f :: Int -> Int
-- > let g::Int->Int; g i = i * 2
-- > let composed1= f . g
-- > composed1 5
-- 11
-- > let composed2= g . f
-- > composed2 5
-- 12
-- >

f1 :: Int -> Int
f1 i = i + 10

f2 :: String -> Int
f2 = read   -- param reduction

f3 :: Int -> Double
f3 i = fromIntegral i / 2.0  -- function invoked before division

-- EXERCISE
-- composed = ???  // use f1 f2 f3
-- ASSERTIONS
-- composed "21"  should be 15.5
-- composed "30"  should be 20.0


-- DEMO 2 - Multi param functions with touples
-- > :t curry
-- curry :: ((a, b) -> c) -> a -> b -> c
-- > let f2 :: (Int,Int) -> Int; f2 (i1,i2) = i1 * i2
-- > :t f2
-- f2 :: (Int, Int) -> Int
-- > :t curry f2
-- curry f2 :: Int -> Int -> Int

f22 :: (Int, Int) -> Int
f22 (i1,i2) = i1 * i2

-- EXERCISE
-- composed = ???   use f22 and read
-- ASSERTION
-- composed "10" 3 should be 30


-- DEMO 3 - foldr
-- > :t foldr
-- foldr :: Foldable t => (a -> b -> b) -> b -> t a -> b
-- > :t foldr (+)
-- foldr (+) :: (Num b, Foldable t) => b -> t b -> b
-- > :t foldr (+) 0
-- foldr (+) 0 :: (Num b, Foldable t) => t b -> b
-- > :t foldr (+) 0 [1,2,3,4,5]
-- foldr (+) 0 [1,2,3,4,5] :: Num b => b
-- >  foldr (+) 0 [1,2,3,4,5]
-- 15
-- > let sum tabl = foldr (+) 0 tabl
-- > sum [1,2,3,4,5]
-- 15
-- > let sumf = foldr (+) 0
-- > sumf [1,2,3,4,5]
-- 15
-- > foldr (\ x y -> x+y) 0 [1,2,3,4,5]
-- 15

-- EXERCISE
--  prodf = ???
-- ASSERTION
-- > prodf [1,2,3,4,5] should be 120

-- EXERCISE
-- reverse = ???
-- ASSERTION
 -- reverse [1,2,3,4,5]  should be [5,4,3,2,1]