import React, { Component } from 'react';
import PropTypes from "prop-types";
import { updateStore } from "../../utils/action.js";
import { connect } from "react-redux";
import requester from "../../utils/requester.js";
import cone from "../../assets/cone.svg";


export class QuizCard extends Component {


	updateNumber = () => {
		updateStore({ numberAttempted: this.props.numberAttempted + 1 })(this.props.dispatch);
	}

	render() {

		const marchText1 = `It should be completed by the ides of March 2019, `;
		const marchText2 = `so check back in a couple weeks!`;
		return (
			<div className="QuizCard">
				<div className="underConstructionRow">
					<span className="underConstruction">this site is under active construction... </span>
					<div className="subRow">
						<img className="workingIcon" src={cone} />
						<span className="march">
							<span className="block">{marchText1}</span>
							<span className="block">{marchText2}</span>
						</span>
					</div>
				</div>
	        	<div className="button" onClick={this.updateNumber}>click to update number</div>

	        	<div className="box">
	        		{this.props.numberAttempted}
	        	</div>

			</div>
		)
	}


}

QuizCard.propTypes = {
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

export default connect(mapStateToProps)(QuizCard);