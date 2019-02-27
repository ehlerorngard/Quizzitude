import React, { Component } from 'react';
import PropTypes from "prop-types";
import { updateStore } from "../../utils/action.js";
import { connect } from "react-redux";
import requester from "../../utils/requester.js";
import Nav from "../../components/Nav/Nav.js";
import QuizCard from "../../components/QuizCard/QuizCard.js";
import QuestionsCard from "../../components/QuestionsCard/QuestionsCard.js";


export class Quizzitude extends Component {
	constructor(props) {
		super(props);
	}

	componentDidMount() {
		updateStore({ numberAttempted: 0, numberCorrect: 0 })(this.props.dispatch)

		requester.getSticks().then(res => console.log("RES DATA! ", res.data));

		console.log("this.props:", this.props);

		requester.getHuman("1003").then(res => console.log("REZZZ BABAY! ", res));
	}

	updateNumber = () => {
		updateStore({ numberAttempted: this.props.numberAttempted + 1 })(this.props.dispatch);
	}


  render() {
	
    return (
        <div className="Quizzitude">
        	<Nav />
			<div className="Cardtainer">
				<QuizCard />
			</div>
        </div>
    );
  }
}

Quizzitude.propTypes = {
  // field3: PropTypes.oneOfType([PropTypes.array, PropTypes.bool]),
	numberCorrect: PropTypes.number,
	numberAttempted: PropTypes.number,
	sticks: PropTypes.string,
}

const mapStateToProps = (state) => {
	return {
		numberAttempted: state.numberAttempted,
		numberCorrect: state.numberCorrect,
		sticks: state.sticks,
	}
}

export default connect(mapStateToProps)(Quizzitude);