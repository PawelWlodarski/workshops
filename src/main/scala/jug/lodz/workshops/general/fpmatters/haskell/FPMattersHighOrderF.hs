-- DEMO High order function with foldr example
  -- *> :t foldr
  -- foldr :: Foldable t => (a -> b -> b) -> b -> t a -> b
  -- *> :t foldr (\ x y -> x+y)
  -- foldr (\ x y -> x+y) :: (Num b, Foldable t) => b -> t b -> b
  -- *> :t foldr (\ x y -> x+y) 0
  -- foldr (\ x y -> x+y) 0 :: (Num b, Foldable t) => t b -> b
  -- *> :t foldr (\ x y -> x+y) 0 [1,2,3,4,5]
  -- foldr (\ x y -> x+y) 0 [1,2,3,4,5] :: Num b => b
  -- *> foldr (\ x y -> x+y) 0 [1,2,3,4,5]
  -- 15


  -- > let curried :: Int -> Int -> Int; curried x y = x+y
  -- > curried 4 3
  -- 7
  -- > let applyTwice :: (Int -> Int) -> Int -> Int;applyTwice f x = f (f x)
  -- > applyTwice (\x->x+1) 1
  -- 3

joinString :: String -> String -> String
joinString s1 s2 = if s2 /= "" then  s1 ++ "," ++ s2 else s1

intToString :: Int -> String
intToString i = show i


-- EXERCISE - implement joinInts , uncomment test1 and check assertions
-- joinInts :: Int -> String -> String
-- joinInts = ???
--
-- test1 :: [Int] -> String
-- test1 = foldr joinInts ""

-- ASSERTIONS
-- > test1 [1,2,3,4,5]
-- "1,2,3,4,5"
-- > test1 [1,2,3]
-- "1,2,3"
-- > test1 [1 .. 20]
-- "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20"


-- DEMO filter - function polymorphism vs class polymorphism
-- > :t filter
-- filter :: (a -> Bool) -> [a] -> [a]
-- > :t (>3)
-- (>3) :: (Num a, Ord a) => a -> Bool
-- > filter (>3) [1..10]
-- [4,5,6,7,8,9,10]

-- EXERCISE - implement with foldr
  -- clean code haskell way - f,p,xs
-- customFilter :: (a -> Bool) -> [a] -> [a]
-- customFilter p xs= foldr concatIfTrue [] xs
--   where concatIfTrue current acc= ???

-- ASSERTIONS
-- customFilter (>3) [1..10]
-- [4,5,6,7,8,9,10]
-- > customFilter (>'e') ['a'..'k']
-- "fghijk"

  -- DEMO map - function polymorphism
  -- > :t map
  -- map :: (a -> b) -> [a] -> [b]
  -- > map (+1) [1..5]
  -- [2,3,4,5,6]

-- EXERCISE - implement with foldr
  -- clean code haskell way - f,p,xs
-- customMap :: (a -> b) -> [a] -> [b]
-- customMap f xs = foldr concatMapped [] xs
--   where
--     concatMapped e acc = ???

-- ASERTIONS
-- > customMap (+1) [1..5]
-- [2,3,4,5,6]
-- > customMap length  ["aa","bbbbb","ccc"]
-- [2,5,3]

-- COMPOSITION EXAMPLE
type Product = (String,Int)
products :: [Product]
products=[("Car",1000),("Pc",50),("Banana",10)]

premium :: [Product] -> [Product]
premium = filter (\p -> snd p > 30)

names :: [Product] -> [String]
names = map fst

joinStringLine :: [String] -> String
joinStringLine = foldr joinString ""

jsonString :: String -> String
jsonString str = "{'premiumProducts' : ["++str++"]}"

-- composition
listNamesJson :: [Product] -> String
listNamesJson = jsonString . joinStringLine . names . premium
