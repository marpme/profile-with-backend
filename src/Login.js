import React, { Component } from 'react'
import lock from './lock.svg'
import user from './user.svg'

export default ({ that, failed }) => (
	<form className="form login" onSubmit={that.onSubmit}>
		<div className="form__field">
			<label htmlFor="login__username">
				<img src={user} className="icon" />
				<span className="hidden">Username</span>
			</label>
			<input
				id="login__username"
				type="text"
				name="username"
				className="form__input"
				placeholder="Username"
				value={that.state.uername}
				onChange={event =>
					that.setState({ username: event.target.value })
				}
				required
			/>
		</div>

		<div className="form__field">
			<label htmlFor="login__password">
				<img src={lock} className="icon" />
				<span className="hidden">Password</span>
			</label>
			<input
				id="login__password"
				type="password"
				name="password"
				className="form__input"
				placeholder="Password"
				value={that.state.password}
				onChange={event =>
					that.setState({ password: event.target.value })
				}
				required
			/>
		</div>

		<div className="form__field">
			<input
				type="submit"
				style={{ backgroundColor: failed ? '#e74c3c' : '#3498db' }}
				value={!failed ? 'Sign In 🔑' : 'Login Failed ⛔️'}
			/>
		</div>
	</form>
)
