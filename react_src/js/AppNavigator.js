'use strict';
import React, {
	Component
} from 'react';
import {
	Text,
	View,
	StyleSheet,
	Navigator
} from 'react-native';

export default class AppNavigator extends Component {
	constructor(props) {
		super(props);
	}
	render() {
		return ( < Navigator initialRoute = {
				{
					//配置路由
					id: this.props.id,
					data: this.props.data,
					name: this.props.name,
					component: this.props.component
				}
			}
			renderScene = {
				(route, navigator) => {
					let Scene = route.component;
					return <Scene 
					        id={route.id} 
					        data={route.data} 
					        name={route.name} 
					        component={route.component}
					        navigator={navigator}
					        />
				}
			}
			style = {
				styles.navigatorstyle
			}
			configureScene = {
				(route) => {
					//配置路由跳转效果
					if (route.sceneConfig) {
						return route.sceneConfig;
					}
					return Navigator.SceneConfigs.PushFromRight;
				}
			}
			/>
		);
	}
}

const styles = StyleSheet.create({
	navigatorstyle: {
		flex: 1
	}
});