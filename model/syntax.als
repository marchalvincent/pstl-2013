

module syntax


open util/integer
open util/boolean

abstract sig Object {}

/************************************************************************
fUML.Syntax.Classes.Kernel
************************************************************************/
abstract sig RedefinableElement extends NamedElement {
	//isLeaf : Boolean,
	//redefinedElement : set RedefinableElement,
	//redefinitionContext : set Classifier
}
abstract sig Element extends Object {
	//ownedElement : set Element,
	//owner : lone Element,
	//ownedComment : set Comment
} 

abstract sig NamedElement extends 	Element {
	//name : String,
	//visibility : VisibilityKind,
	//qualifiedName : String,
	//namespace : Namespace
}

abstract sig Class extends BehavioredClassifier {
	//isAbstract : Boolean,
	//ownedOperation : set Operation,
	//superClass : set Class,
	//isActive : Boolean,
	//ownedReception : set Reception,
	//ownedAttribute  : set Property,
	//nestedClassifier : set Classifier
}

abstract sig Classifier extends Type {
	//isAbstract : Boolean,
	//generalization : set Generalization,
	//feature : set Feature,
	//inheritedMember : set NamedElement,
	//attribute : set Property,
	//general : set Classifier,
	//isFinalSpecialization : Boolean
}

abstract sig Type extends Namespace {
	//package : Package
}

abstract sig Namespace extends PackageableElement {
 	//member : set NamedElement,
 	//ownedMember  : set NamedElement,
 	//elementImport : set ElementImport,
 	//packageImport : set PackageImport;
 	//importedMember : set PackageableElement
}

abstract sig PackageableElement extends NamedElement {}

abstract sig MultiplicityElement extends Element {
	//isOrdered : Boolean
	//isUnique : Boolean
	upper : Int,
	lower : Int
	//ValueSpecification upperValue = null;
	//ValueSpecification lowerValue = null;
}

abstract sig TypedElement extends NamedElement {
	//type : Type;
}

abstract sig Parameter extends TypedElement {
	//multiplicityElement : MultiplicityElement()
	//direction : ParameterDirectionKind.in;
	//operation : Operation;
}



/************************************************************************
fUML.Syntax.Activities.IntermediateActivities
************************************************************************/

abstract sig ActivityEdge extends RedefinableElement {
	//activity : Activity,
	source : ActivityNode,
	target : ActivityNode,
	//guard : ValueSpecification,
	//inStructuredNode : StructuredActivityNode
}

abstract sig ActivityNode extends RedefinableElement {
	//inStructuredNode : StructuredActivityNode,
	//activity : Activity,
	outgoing : set ActivityEdge,
	incoming : set ActivityEdge
}

abstract sig ControlNode extends ActivityNode {}
abstract sig FinalNode extends ControlNode {}
abstract sig ActivityFinalNode extends FinalNode {}

abstract sig Activity extends Behavior {
	//structuredNode = set StructuredActivityNodeList,
	//node : set ActivityNode,
	//isReadOnly : Boolean,
	//edge : set ActivityEdge();
}

abstract sig ActivityParameterNode extends ObjectNode {
	//parameter : Parameter
}

abstract sig ObjectNode extends ActivityNode {
	//typedElement : TypedElement;
}

abstract sig ControlFlow extends ActivityEdge {}
abstract sig ObjectFlow extends ActivityEdge {}

abstract sig DecisionNode extends ControlNode {
	//decisionInput : lone Behavior,
	//decisionInputFlow : lone ObjectFlow 
}

abstract sig MergeNode extends ControlNode {}

abstract sig ForkNode extends ControlNode {}
abstract sig JoinNode extends ControlNode {}

abstract sig InitialNode extends ControlNode {}




/************************************************************************
fUML.Syntax.CommonBehaviors.BasicBehaviors
************************************************************************/

abstract sig Behavior extends Class {
	//isReentrant : Boolean,
	//specification : BehavioralFeature,
	//ownedParameter : set Parameter,
	//context : BehavioredClassifier
}

abstract sig BehavioredClassifier extends Classifier {
	//ownedBehavior : set Behavior;
	//classifierBehavior : Behavior;

}


/************************************************************************
fUML.Syntax.CommonBehaviors.Communications
************************************************************************/
// Event, MessageEvent, Reception, Signal



/************************************************************************
fUML.Syntax.Activities.ExtraStructuredActivities
************************************************************************/
abstract sig ExpansionKind extends Object {}
abstract sig EK_parallel, EK_iterative, EK_stream extends ExpansionKind {}

abstract sig ExpansionNode extends ObjectNode {
	//regionAsOutput : lone ExpansionRegion,
	//regionAsInput : lone ExpansionRegion
}

abstract sig ExpansionRegion extends StructuredActivityNode {
	//mode : ExpansionKind,
	//outputElement : set ExpansionNode,
	//inputElement : set ExpansionNode
}

/************************************************************************
fUML.Syntax.Activities.CompleteStructuredActivities
************************************************************************/
abstract sig Clause extends Element {
	//test : set ExecutableNode,
	//body : set xecutableNode,
	//predecessorClause : set Clause,
	//successorClause : set Clause,
	//decider : OutputPin,
	//bodyOutput : set OutputPinList,
}

abstract sig ConditionalNode extends StructuredActivityNode {
	//isDeterminate : Boolean,
	//isAssured  : Boolean,
	//clause : set Clause,
	//result : set OutputPin
}

abstract sig ExecutableNode extends ActivityNode {}

abstract sig LoopNode extends StructuredActivityNode {
	//isTestedFirst : Boolean,
	//decider : OutputPin,
	//test : set ExecutableNode,
	//bodyOutput : set OutputPin,
	//loopVariableInput : set InputPin,
	//bodyPart : set ExecutableNode,
	//result : set OutputPin,
	//loopVariable : set OutputPin,
	//setupPart : set ExecutableNode
}

abstract sig StructuredActivityNode extends Action {
	//node : set ActivityNode,
	//activity : Activity,
	//mustIsolate : Boolean,
	//edge : set ActivityEdge,
	//structuredNodeOutput : set OutputPin,
	//structuredNodeInput : set InputPin
}

abstract sig OpaqueAction extends Action {
	//node : set ActivityNode,
	//activity : Activity,
	//mustIsolate : Boolean,
	//edge : set ActivityEdge,
	//structuredNodeOutput : set OutputPin,
	//structuredNodeInput : set InputPin
}


/************************************************************************
fUML.Syntax.Actions.BasicActions
************************************************************************/
abstract sig Action extends ExecutableNode {
	output : set OutputPin,
	//context : Classifier,
	input : set InputPin,
	//isLocallyReentrant : Boolean
} 

abstract sig CallAction extends InvocationAction {
	//isSynchronous : Boolean,
	//result : set OutputPin
}

abstract sig CallBehaviorAction extends CallAction {
	//behavior : Behavior
}

abstract sig CallOperationAction extends CallAction {
	//operation : Operation
	//target : InputPin
}

abstract sig SendSignalAction extends InvocationAction {
	//target : InputPin 
	//signal : Signal 
}

abstract sig InputPin extends Pin {}
abstract sig OutputPin extends Pin {}

abstract sig Pin extends ObjectNode {
	multiplicityElement : MultiplicityElement
}

abstract sig InvocationAction extends Action {
	//argument : set InputPin
}


/************************************************************************
fUML.Syntax.Actions.CompleteActions
************************************************************************/
// AcceptEventAction, ReadExtentAction, 



/************************************************************************
fUML.Syntax.Actions.IntermediateActions
************************************************************************/
// AddStructuralFeature, WriteStructuralFeature...





/************************************************************************
sig - help to write simple process
************************************************************************/

one sig MultiZeroOne extends MultiplicityElement {} {
	lower = 0
	upper = 1
}
one sig MultiOneOne extends MultiplicityElement {} {
	lower = 1
	upper = 1
}
one sig MultiZeroStar extends MultiplicityElement {} {
	lower = 0
	upper = max[] 
}
one sig MultiOneStar extends MultiplicityElement {} {
	lower = 1
	upper = max[] 
}
one sig MultiTwoStar extends MultiplicityElement {} {
	lower = 2
	upper = max[] 
}
one sig MultiStarStar extends MultiplicityElement {} {
	lower = max[]
	upper = max[] 
}


fact edgeDifferentTargetSource {
	all n : ActivityEdge | {
		not n.source = n.target
	}
}

fact nodeAndEdgeConnectivity {
	all node : ActivityNode, edge : ActivityEdge | edge.source = node    implies edge in node.outgoing
	and 
	all node : ActivityNode, edge : ActivityEdge | edge.target  = node    implies edge in node.incoming
	and
	all node : ActivityNode, edge : ActivityEdge | edge in node.outgoing implies edge.source = node
	and
	all node : ActivityNode, edge : ActivityEdge | edge in node.incoming  implies edge.target     = node
}


// Une pin appartiens a seulement 1 seul Action 
fact pin {
	all disj n1, n2 : Action, p : OutputPin | not (p in n1.output and p in n2.output)
	and
	all disj n1, n2 : Action, p : InputPin | not (p in n1.input and p in n2.input)
}


fact onlyOneEdgeWithSameSourceDestination {
	no disj e1,e2 : ActivityEdge | e1.source = e2.source and e1.target = e2.target
}





/************************************************************************
OCL
************************************************************************/

fact action {
	all node : Action | #node.outgoing > 0 or  #node.incoming > 0 // An action must be connected
}

// TODO ControlFlow, ObjectFlow, ActivityEdge

/** ControlFlow */
/** ObjectFlow */
fact objectFlow {
	all n : ObjectFlow | {
		//Package BasicActivities
		not n.target in Action // [1] Object flows may not have actions at either end.
		//Package CompleteActivities
/*
Package BasicActivities

[2] Object nodes connected by an object flow, with optionally intervening control nodes, must have compatible types. In particular, the downstream object node type must be the same or a supertype of the upstream object node type.
[3] Object nodes connected by an object flow, with optionally intervening control nodes, must have the same upper bounds.
Package CompleteActivities
[1] An edge with constant weight may not target an object node, or lead to an object node downstream with no intervening actions, that has an upper bound less than the weight.
[2] A transformation behavior has one input parameter and one output parameter. The input parameter must be the same or a supertype of the type of object token coming from the source end. The output parameter must be the same or a subtype of the type of object token expected downstream. The behavior cannot have side effects.
[3] An object flow may have a selection behavior only if it has an object node as a source.
[4] A selection behavior has one input parameter and one output parameter. The input parameter must be a bag of elements of the same or a supertype of the type of source object node. The output parameter must be the same or a subtype of the type of source object node. The behavior cannot have side effects.
[5] isMulticast and isMultireceive cannot both be true.
*/
	}
}

/** ObjectNode */
fact objectNode {
	all n : ObjectNode | {
		//Package BasicActivities
		all e : n.incoming | e in ObjectFlow
		and
		all e : n.outgoing | e in ObjectFlow
		//Package CompleteActivities
/*
Package BasicActivities
[1] All edges coming into or going out of object nodes must be object flow edges.
Package CompleteActivities
[1] If an object node has a selection behavior, then the ordering of the object node is ordered and vice versa.
[2] A selection behavior has one input parameter and one output parameter. The input parameter must be a bag of elements of the same type as the object node or a supertype of the type of object node. The output parameter must be the same or a subtype of the type of object node. The behavior cannot have side effects.
*/
	}
}


/** InitialNode */
fact initialNode {
	all n : InitialNode | {
		#n.incoming = 0 // [1] An initial node has no incoming edges.
	}
}
fact initialNode2 {
	no edge : ObjectFlow | edge.source in InitialNode // [2] Only control edges can have initial nodes as source.
}

/** ActivityFinalNode */
fact activityFinalNode {
	all n : ActivityFinalNode | {
		#n.outgoing = 0//	[1] A final node has no outgoing edges.
	}
}

/**  MergeNode */
fact mergeNode {
	all n : MergeNode | {
		#n.outgoing = 1 // [1] A merge node has one outgoing edge.
		and
		// [2] The edges coming into and out of a merge node must be either all object flows or all control flows.
		all edgeIn : n.incoming, edgeOut : n.outgoing | 
			(edgeIn in ControlFlow and edgeOut in ControlFlow)
			or
			(edgeIn in ObjectFlow and edgeOut in ObjectFlow)
		
	}	
}

/** DecisionNode */
fact decisionNode {
	all n : DecisionNode | {
		#n.incoming = 1 and #n.outgoing >= 1// [1] A decision node has one or two incoming edges and at least one outgoing edge.
	}
/*
[2] The edges coming into and out of a decision node, other than the decision input flow (if any), must be either all object flows or all control flows.
[3] The decisionInputFlow of a decision node must be an incoming edge of the decision node.
[4] A decision input behavior has no output parameters, no in-out parameters and one return parameter.
[5] If the decision node has no decision input flow and an incoming control flow, then a decision input behavior has zero input parameters.
[6] If the decision node has no decision input flow and an incoming object flow, then a decision input behavior has one input parameter whose type is the same as or a supertype of the type of object tokens offered on the incoming edge.
[7] If the decision node has a decision input flow and an incoming control flow, then a decision input behavior has one input parameter whose type is the same as or a supertype of the type of object tokens offered on the decision input flow.
[8] If the decision node has a decision input flow and a second incoming object flow, then a decision input behavior has two input parameters,
• the first of which has a type that is the same as or a supertype of the type of object tokens offered on the non- decision input flow, and
• the second of which has a type that is the same as or a supertype of the type of object tokens offered on the decision input flow.
*/
}


/** ForkNode */

fact forkNode {
	all n : ForkNode | {
		#n.incoming = 1//[1] A fork node has one incoming edge.
		//[2] The edges coming into and out of a fork node must be either all object flows or all control flows.
	}	
}

/* not working, TODO fix
fact joinNode {
	all n : JoinNode | {
		#n.outgoing = 1//[1] A join node has one outgoing edge.
		and
		//[2] If a join node has an incoming object flow, it must have an outgoing object flow, otherwise, it must have an outgoing control flow.
		one edge : n.incoming | edge in ObjectFlow implies 
			(one o : n.outgoing | o in ObjectFlow)
		else
			(one o : n.outgoing | o in ControlFlow)
	}
}
*/

/*
pred safe[s: State] {…}
assert allReachableSafe {
all s: State | safe[s]
}
*/

run {#ActivityEdge > 2 and #ActivityNode > 2} for 15 Object





