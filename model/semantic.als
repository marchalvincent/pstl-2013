
module semantic

open util/ordering[State]
open util/integer
open util/boolean
open syntax


/*
sig SemanticVisitor {}

TODO local state pattern

sig ActivityNodeActivation extends SemanticVisitor {
	//group : ActivityNodeActivationGroup,
	//node : ActivityNode,
	//incomingEdges : set ActivityEdgeInstance,
	//outgoingEdges : set ActivityEdgeInstance,
	//running : Boolean,
	//heldTokens : Token
	heldTokens : Int -> State
}
*/


abstract sig Status {}
one sig Running, Finished extends Status {}



/** Process Informations */
abstract sig Timing {
	timing : ActivityNode -> Int
}
abstract sig RolePerformer {}
abstract sig Performer {
	performer : ActivityNode -> set RolePerformer,
}


sig State {
	/** Semantic */
	// ActivityNodeActivation
	heldTokens :  ActivityNode -> one Int,

	// ActivityEdgeInstance
	offers :  ActivityEdge -> one Int,

	/** Process Info */
	time : Int,

	/** VM */
	running : Status
	
}



/*
// NOT WORKING on infinite process
// predicate are inconsistent if not enough trace a provided
fact properEnd {
	some s:State | s.running = Finished
}
*/
/*

fact decisionChoiceFair {
	all decision:DecisionNode | fairDecision[decision]
}
pred fairDecision[node:DecisionNode] {
		// if the node is actived more or equal than his number of outoing edge
		#{  getNumberOfActivation[node] } >= #node.outgoing 
			// implies that all of its outgoing edge need to have at least an offer during the trace
			// -> (decision made by the node are fair)
			implies #{ edge : node.outgoing | edgeGotOffer[edge] } = #node.outgoing
}
// an Activation is counted when the number of Tokens from the node go to 0 from 1
fun getNumberOfActivation[node:ActivityNode] : State->State {
	{ s1,s2 : State | s1.next = s2 and s1.getTokens[node] = 1 and s2.getTokens[node] = 0  }
}
pred edgeGotOffer [edge:ActivityEdge] { 
	some s1: State | s1.getOffers[edge] = 1
}
*/

// getters
pred State.hasOffers[edge:ActivityEdge] { this.getOffers[edge] > 0 }
pred State.hasTokens[node:ActivityNode]  { this.getTokens[node] > 0 }
fun State.getTokens[node:ActivityNode] : Int { this.heldTokens[node] }
fun State.getOffers  [edge:ActivityEdge] : Int { this.offers[edge] }

fun getTiming[n:ActivityNode] : Int { Timing.timing[n] }

// setters
pred State.setTokens[node:ActivityNode, i : Int]  { this.heldTokens[node] =  i}
pred State.setOffers  [edge:ActivityEdge, i : Int]  { this.offers[edge] = i}

pred setExecuting[s,s':State] { s'.running = Running }

// block Edge and Node 
pred blockAll[s,s':State] { blockAllNode[s,s',ActivityNode] and blockAllEdge[s,s',ActivityEdge]}
pred blockAllEdge[s,s':State,edge: set ActivityEdge] { all e : edge | blockEdge[s,s',e] }
pred blockAllNode[s,s':State,node: set ActivityNode] { all n : node | blockNode[s,s',n] }
pred blockEdge[s,s':State, edge:ActivityEdge] {s.getOffers[edge] = s'.getOffers[edge]}
pred blockNode[s,s':State, node:ActivityNode] { s.getTokens[node] = s'.getTokens[node] }



/************************************************************************
Transition Systems
************************************************************************/
pred Init[ n:ActivityNode->Int, e:ActivityEdge->Int]
{
	first.heldTokens    = (ActivityNode -> 0)  ++ n
	first.offers = (ActivityEdge -> 0) ++ e
	first.running = Running
	first.time = 0
}

fact traces {
	all s: State - last | let s' = s.next | {
		s.running = Running implies {
			// get nodes ready to finish and start
			let finish = { n:ActivityNode-Pin | s.isReadyToFinish[n] } | 
			let start = { n:ActivityNode-Pin | s.isReadyToStart[n] } | {
				// if both finish and nodes, either start or finish on a node
				(#start > 0 and #finish > 0) implies {
					(one n:finish | StepFinishNode[s,s',n])
					 or 
					(one n:start | StepStartNode[s,s',n])
				}
				// finish the execution of one node
				else (#finish > 0) implies {
					(one n:finish | StepFinishNode[s,s',n])
				}
				// start the execution of one node
				else (#start > 0) implies {
					(one n:start | StepStartNode[s,s',n])
				// nothing can be done, termination by stuttering the last state
				} else  {
					endLoop[s,s']
				}
			}
		} else {
			// stuttering the last state
			endLoop[s,s']
		}
	}
}

pred endLoop[s,s':State] {
	blockAll[s,s']	
	s'.running = Finished 
	s'.time = s.time
}


/************************************************************************
CallBack
************************************************************************/
pred callbackNodeStart[s,s':State, node:ActivityNode] {
	s'.time = s.time.add[getTiming[node] ]
}
pred callbackNodeFinish[s,s':State, node:ActivityNode] {
	s'.time = s.time
}


/************************************************************************
Step Finish
************************************************************************/
pred State.isReadyToFinish[node:ActivityNode] {
	// the node is already executing
	this.hasTokens[node] 
	and
	// the node owns outgoing edge
	#node.outgoing > 0
}

pred StepFinishNode[s , s' : State, node:ActivityNode] {
	// set the status to executing
	setExecuting[s,s']
	// callback notification
	callbackNodeFinish[s,s',node]

	// Action
	node in Action implies {
		s'.setTokens[node, 0]
		// add offers to both outgoing edge and outgoing edge of output
		// the number of added offers corresponds to the number of tokens on the node
		all edge : (node.outgoing+node.output.outgoing) | {
			s'.setOffers[edge, s.getOffers[edge].add[s.getTokens[edge.source]]]	 
		}
		// reset input and output pin
		all pin : (node.output+node.input) | {
			s'.setTokens[pin, 0]
		}
		blockAllEdge[s,s',(ActivityEdge-(node.outgoing+node.output.outgoing))]	
		blockAllNode[s,s',(ActivityNode-(node+node.input+node.output))]						
	} 

	// DecisionNode
	else node in DecisionNode implies {
		s'.setTokens[node, 0]
		// offer tokens on only one outgoing edge
		one edge : node.outgoing | {
			s'.setOffers[edge, s.getOffers[edge].add[s.getTokens[edge.source]]]	 
			blockAllEdge[s,s',(ActivityEdge-edge)]
		}
		blockAllNode[s,s',(ActivityNode-node)]
	}		
	 
	// (default) ActivityNode
	else node in ActivityNode implies {	
		s'.setTokens[node, 0]
		all edge : node.outgoing | {
			s'.setOffers[edge, s.getOffers[edge].add[s.getTokens[edge.source]]]	 
		}
		blockAllEdge[s,s',(ActivityEdge-node.outgoing)]
		blockAllNode[s,s',(ActivityNode-node)]
	}
}

/************************************************************************
Step Start
************************************************************************/

/* isReady() */
pred State.isReadyToStart(node:ActivityNode) {
	// the node is not already executing
	not this.hasTokens[node]
	and	
	// the node owns incoming edge
	#node.incoming > 0 
	and
	// if the node is a MergeNode
	// only 1 incoming edge with offers is required
	(node in MergeNode implies { 
		#{ n1:node.incoming | this.hasOffers[n1] } >= 1
	}
	// if the node is a Action
	// check if all the incoming edge have offers
	// check if all the input pin have enough offers depending of their multiplicity
	else node in Action implies {
		#{ n1:node.incoming | this.hasOffers[n1] } = #node.incoming
		and
		#{ n:node.input.incoming | this.getOffers[n] >=n.target.multiplicityElement.lower } = #node.input.incoming
	} 
	// (default) If the node is a ActivityNode
	// check if all the incoming edge have offers
	else node in ActivityNode implies {	
		#{ n1:node.incoming | this.hasOffers[n1] } = #node.incoming
	})	
}

/* receiveOffer() */
pred StepStartNode[s , s' : State, node : ActivityNode] {
	// set the status to executing
	setExecuting[s,s']
	// callback notification
	callbackNodeStart[s,s',node]

	// Action
	node in Action implies {	
		s'.setTokens[node, 1]
		// set the number of consumed tokens in the input pin
		// trying to consume all offered tokens	
		all iPin : node.input | {
			iPin.multiplicityElement.upper >= s.getOffers[iPin.incoming]
			implies
				// consume all
				s'.getTokens[iPin] = s.getOffers[iPin.incoming]	
			else
				// consume up to upper
				s'.getTokens[iPin] = iPin.multiplicityElement.upper 
		}
		// set the number of tokens in the output pin
		// nondeterministic between lower and upper multiplicity
		all oPin : node.output | {
			s'.getTokens[oPin] >= oPin.multiplicityElement.lower
			and	
			s'.getTokens[oPin] <= oPin.multiplicityElement.upper
		}
		// remove offers from both incoming edge and incoming edge of input
		// the number of removed offers corresponds to the new number of tokens on the node
		all edge : (node.incoming+node.input.incoming)  | {
			s'.setOffers[edge, s.getOffers[edge].sub[s'.getTokens[edge.target]]]	 
		}
		blockAllEdge[s,s',ActivityEdge-(node.incoming+node.input.incoming) ]
		blockAllNode[s,s',ActivityNode-(node+node.input+node.output)]
	}

	// MergeNode
	else node in MergeNode implies {	
		s'.setTokens[node, 1]
		// choose one incoming edge
		one edge : node.incoming | {  
			// the choosed edge need to have offers
			s.hasOffers[edge]
			// remove offers from the edge
			s'.setOffers[edge, s.getOffers[edge].sub[s'.getTokens[edge.target]]]	 
			blockAllEdge[s,s',(ActivityEdge-edge)]
		}
		blockAllNode[s,s',(ActivityNode-node)]
	}	

	// (default) ActivityNode
	else  node in ActivityNode implies {
		s'.setTokens[node, 1]
		all edge : node.incoming | {
			s'.setOffers[edge, s.getOffers[edge].sub[s'.getTokens[edge.target]]]	 
		}
		blockAllEdge[s,s',(ActivityEdge-node.incoming)]
		blockAllNode[s,s',(ActivityNode-node)]
	}
}









