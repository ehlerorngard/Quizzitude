import React, { Component } from 'react';
import PropTypes from "prop-types";
import { updateStore } from "../../utils/action.js";
import { connect } from "react-redux";
import requester from "../../utils/requester.js";


export class QuestionsCard extends Component {


	render() {
		return (
			<div className="QuestionsCard">


			</div>
		)
	}


}

QuestionsCard.propTypes = {
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

export default connect(mapStateToProps)(QuestionsCard);