

module process

open syntax
open semantic

fact initTokens {
	Init[  
		Initial -> 1 , 			// tokens //TODO pour chaque noeud Initial, NOM1 -> 1 + NOM2 -> 1 + 
		ActivityEdge -> 0  // offers	
	]
}

// Timing
one sig T extends Timing {} {
	timing = (ActivityNode -> 0) 
}

// Role Performer
one sig Yoann extends RolePerformer {}
one sig P extends Performer {} {
	performer = ActivityNode -> Yoann
}


//TODO pour chaque Nodes de l'activité, déclarer
// one sig NODENAME extends TYPENODE
one sig Initial      extends InitialNode {} {} 
one sig ActionA extends Action {}{
}
one sig ActionB extends Action {}{
}
one sig ActionC extends Action {}{
}
one sig Final       extends ActivityFinalNode {} {} 

//TODO pour chaque Edges de l'activité, déclarer:
//one sig EDGENAME extends EDGETYPE { source = EDGESOURCE, target = EDGETARGET }
one sig InitialActionA extends ControlFlow {} {
	source = Initial
	target = ActionA
}
one sig ActionAActionB extends ControlFlow {} {
	source = ActionA
	target = ActionB
}
one sig ActionBActionC extends ControlFlow {} {
	source = ActionB
	target = ActionC
}
one sig ActionCFinal extends ControlFlow {} {
	source = ActionC
	target = Final
}


pred final {	
	//some s : State | s.getTokens[Final] = 1 // 4 Solution
	some s:State | s.getTokens[Final] > 0
}



/////////////


pred testAll {
	final 
}

assert tall {
	testAll
}

//TODO le nombre peux State peux augmenter ex: 20 State ou 30 State etc...
run testAll for 0 but 20 State ,  15 Object, 5 ActivityNode, 4 ActivityEdge expect 1
check tall for 20 State ,  15 Object, 5 ActivityNode, 4 ActivityEdge expect 0




/** *Visualization Variables */
// http://alloy.mit.edu/community/node/548
fun vNodeExecuting : State->ActivityNode {
   {s:State, a:ActivityNode | s.getTokens[a] > 0}
}
fun vEdgeHaveOffers : State->ActivityEdge {
   {s:State, e:ActivityEdge | s.getOffers[e] > 0}
}

fun pinInNode : State->Action->Pin->Int {
	 {s:State, a:Action, p:a.output+a.input, i:s.getTokens[p]}
}



