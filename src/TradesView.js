import React, { Component } from 'react'
import RestManager from './RestManager'

export default class TradesView extends Component {
	constructor(props) {
		super(props)
		this.state = {
			trades: [],
		}
	}

	componentDidMount() {
		this.getAllTrades()
	}

	getAllTrades() {
		RestManager.getTrades().then(trades =>
			this.setState({
				trades,
			})
		)
	}

	createNewTrade() {}

	render() {
		return (
			<div>
				{this.state.trades
					? this.state.trades.map((trade, index) => (
							<div key={index} className="user-card">
								<hr />
								<div className="user-card-cont">
									<div className="user-card-title">
										{trade.title}
									</div>
									<p className="user-id">Trade #{trade.id}</p>
									<em className="user-card-text">
										{trade.description}
									</em>
									<em className="user-card-text">
										created by{' '}
										{trade.creator && trade.creator.fullName
											? trade.creator.fullName
											: 'anonymous'}
									</em>
								</div>
							</div>
						))
					: null}
			</div>
		)
	}
}
