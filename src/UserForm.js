import React, { Component } from 'react'
import RestManager from './RestManager'
import './UserForm.css'

export default class UserForm extends Component {
	constructor(props) {
		super(props)
		this.state = {
			firstname: null,
		}
	}

	componentDidMount() {
		RestManager.user().then(user => {
			this.setState(user)
		})
	}

	onLogout() {
		RestManager.logout().then(() => {
			RestManager.removeSessionId()
			this.props.setIfLoggedIn()
		})
	}

	render() {
		if (!this.state.firstname) {
			return <h3>Loading your profile.</h3>
		}

		return (
			<div>
				<div className="user-card">
					<div className="user-card-pb card__image--fence" />
					<div className="user-card-img" />
					<div className="user-card-cont">
						<div className="user-card-title">
							{this.state.fullName}
						</div>
						<p className="user-id">Account ID#{this.state.id}</p>
						<em className="user-card-text">
							<b>Rolle:</b> {this.state.profile.role.name}
						</em>
						<em className="user-card-text">
							<b>Username:</b> {this.state.profile.username}
						</em>
					</div>
					<div className="btn-row">
						<button onClick={this.onLogout.bind(this)}>
							Logout
						</button>
					</div>
				</div>
			</div>
		)
	}
}
