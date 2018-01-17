import Cookie from 'js-cookie'

const API_VERSION = 1
const ENDPOINT = `/swxercise/rest/v${API_VERSION}/`

const requestMethods = {
	/**
	 * Pings the server and checks connection
	 * returns boolean
	 */
	ping: () =>
		fetch(`${ENDPOINT}ping`).then(res =>
			res.text().then(text => res.status === 200 && text === 'pong')
		),
	/**
	 * form => { username, password }
	 */
	login: form =>
		fetch(`${ENDPOINT}user/login`, {
			method: 'POST',
			body: JSON.stringify(form),
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(res => res.status === 200 && res.json()),

	logout: () =>
		fetch(`${ENDPOINT}user/logout`, {
			method: 'POST',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(res => res.status === 200 && res.json()),

	user: () =>
		fetch(`${ENDPOINT}user`, {
			method: 'GET',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json',
			},
		}).then(res => res.status === 200 && res.json()),
}

const sessionMethods = {
	isAuthenticated: () => {
		return fetch(`${ENDPOINT}user`, {
			method: 'GET',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json',
			},
		})
			.then(res => res.ok && res.status === 200)
			.catch(() => {
				console.clear()
				// this can and will fail from time to time. No Problem there.
			})
	},

	getSessionID: () => Cookie.get('JSESSIONID'),
	removeSessionId: () => Cookie.remove('JSESSIONID'),
}

export default {
	...sessionMethods,
	...requestMethods,
}
