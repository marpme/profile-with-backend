import React, { Component } from 'react'
import RestManager from './RestManager'
import Login from './Login'
import UserForm from './UserForm'
import TradesView from './TradesView'
import './App.css'

class App extends Component {
	constructor(props) {
		super(props)
		this.state = {
			connected: false,
			username: '',
			password: '',
			authorized: false,
			failed: false,
		}

		this.onSubmit = this.onSubmit.bind(this)
		this.componentDidMount = this.componentDidMount.bind(this)
		this.setIfLoggedIn = this.setIfLoggedIn.bind(this)
	}

	componentDidMount() {
		this.setIfLoggedIn()
		RestManager.ping().then(status => this.setState({ connected: status }))
		setInterval(() => {
			RestManager.ping().then(status =>
				this.setState({ connected: status })
			)
		}, 5000)
	}

	setIfLoggedIn() {
		RestManager.isAuthenticated().then(isAuthenticated => {
			this.setState({ authorized: isAuthenticated })
		})
	}

	onSubmit(e) {
		e.preventDefault()
		const { username, password } = this.state
		RestManager.login({ username, password }).then(resp => {
			if (resp.responseCode === 0) {
				this.setIfLoggedIn()
				this.setState({ failed: false })
			} else {
				this.setState({ failed: true })
				setTimeout(() => {
					this.setState({ failed: false })
				}, 2000)
			}

			this.setState({ username: '', password: '' })
		})
	}

	render() {
		return (
			<div>
				{!this.state.authorized ? (
					<Login that={this} failed={this.state.failed} />
				) : (
					<div>
						<UserForm
							sessionId={this.state.sessionId}
							setIfLoggedIn={this.setIfLoggedIn}
						/>
						<TradesView />
					</div>
				)}
				<p className="text--center">
					Connected?{' '}
					<a>
						{this.state.connected
							? '🔌 Gogogo, request somehting'
							: '⚡️ awee ... errors everywhere'}
					</a>
				</p>
			</div>
		)
	}
}

export default App
