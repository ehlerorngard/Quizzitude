import React, { Component } from 'react';
import PropTypes from "prop-types";
import { updateStore } from "../../utils/action.js";
import { connect } from "react-redux";
import requester from "../../utils/requester.js";


export default class Nav extends Component {


	render() {
		return (
			<div className="Nav">
				<div className="title nav-item">
					<span className="Q">Q</span>
					<span className="izzitude">uizzitude</span>
				</div>
				<div className="navbuttonsContainer nav-item">

				</div>

			</div>
		)
	}


}