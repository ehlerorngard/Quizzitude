import React, { Component } from 'react';
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { Drawer, AppBar, IconButton, List, ListItem } from "material-ui";
import NavigationClose from 'material-ui/svg-icons/navigation/close';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import { updateStore } from "../../utils/action.js";
import "../../Wedding.css";
import Main from "../Main/Main.js";
import Navbar from "../Navbar/Navbar.js";

import ContentInbox from 'material-ui/svg-icons/content/inbox';
import ActionGrade from 'material-ui/svg-icons/action/grade';
import ContentSend from 'material-ui/svg-icons/content/send';
import ContentDrafts from 'material-ui/svg-icons/content/drafts';
import Divider from 'material-ui/Divider';
import ActionInfo from 'material-ui/svg-icons/action/info';

class Cartographer extends Component {

  componentDidMount() {
    this.getScreenSize();
    this.handleScroll();
    window.addEventListener("resize", this.getScreenSize, true);
    window.addEventListener("scroll", this.handleScroll, true);
  }

  componentWillUnmount() {
    window.removeEventListener("resize", this.getScreenSize, true);
    window.removeEventListener("scroll", this.handleScroll, true);
  }

  getScreenSize = () => {
    let screenSize = "";
    if (window.innerWidth < 700) screenSize = "mobile";
    else if (window.innerWidth <= 1000) screenSize = "tablet";
    else if (window.innerWidth < 1000) screenSize = "computer";
    else screenSize = "computer";

    updateStore({ screenSize: screenSize })(this.props.dispatch);
  }

  handleScroll = () => {
    let scrolllocation = 
      (window.pageYOffset < 6)
      ? { scrolledToTop: true }
      : { scrolledToTop: false }

    updateStore(scrolllocation)(this.props.dispatch);
  }

  showSidebar = () => {
    updateStore({ sidebarVisible: true })(this.props.dispatch);
  }

  hideSidebar = () => {
    console.log("hiding sidebar....")
    updateStore({ sidebarVisible: false })(this.props.dispatch);
  }

  renderPresentPanel = () => {
    switch (this.props.where) {
      case "elsewhere":
        return (<div />)
      default:
        return (<Main />)
    }
  }


  render() {
    console.log('this.props Carto console:', this.props);
    const { sidebarVisible } = this.props;
    const style = { cursor: 'pointer' };

    return (
      <div className="cartographer"> 
        <Navbar />
        {this.renderPresentPanel()}
        <MuiThemeProvider>
          <Drawer 
            width={200} 
            className="menubar"
            openSecondary={true}
            open={sidebarVisible} >
            <AppBar 
              className="menuHeader"
              title={<span style={style}>menu</span>} 
              iconElementRight={
                <IconButton onClick={this.hideSidebar}><NavigationClose /></IconButton>}/>
            <List>
              <ListItem primaryText="home" leftIcon={<ContentInbox />} />
              <ListItem primaryText="where & when" leftIcon={<ActionGrade />} />
              <ListItem primaryText="RSVP" leftIcon={<ContentSend />} />
              <ListItem primaryText="schedule" leftIcon={<ContentDrafts />} />
              <ListItem primaryText="where to stay" leftIcon={<ContentInbox />} />
              <ListItem primaryText="what to bring" leftIcon={<ContentInbox />} />
              <ListItem primaryText="contact us" leftIcon={<ContentInbox />} />
            </List>
          </Drawer>
        </MuiThemeProvider>
      </div>
    );
  }
}

Cartographer.propTypes = {
  // field3: PropTypes.oneOfType([PropTypes.array, PropTypes.bool]),
  where: PropTypes.string,
  sidebarVisible: PropTypes.bool,
  scrolledToTop: PropTypes.bool,
  screenSize: PropTypes.string,
}

const mapStateToProps = (state) => {
  return {
    where: state.where,
    sidebarVisible: state.sidebarVisible,
    scrolledToTop: state.scrolledToTop,
    screenSize: state.screenSize,
  }
}

export default connect(mapStateToProps)(Cartographer);