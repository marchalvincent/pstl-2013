abstract sig A {}

sig B {}

one sig A1 extends A {}

one sig A2 extends A {}

A { f: B lone->lone B }
A { g: B }
pred someG { some g }

 run { some A && atMostThree[B,B] } 
 run { some f && SomeG[] } 
