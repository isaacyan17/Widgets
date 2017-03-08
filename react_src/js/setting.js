'use strict';
import React, {
	Component
} from 'react';
import {
	AppRegistry,
	StyleSheet,
	Text,
	View,
	BackAndroid,
	ToastAndroid,
	Platform
} from 'react-native';
import {
	Button,
	List
} from 'antd-mobile';

import Third from './Third';

const Item = List.Item;
const Brief = Item.Brief;

export default class Settings extends Component {
	constructor(props) {
		super(props);
	}

	_onClick() {
		/*测试点击navigator的点击跳转
		方法名和点击事件名一致....*/
		const navigator = this.props.navigator;
		if (navigator) {
			navigator.push({
				id: 'thirdpage',
				data: '',
				name: 'third',
				component: Third
			});
		}
	}

	render() {
		return (
			<View style = {styles.container}>
    		 <List renderHeader={()=> '个人设置1'}>
                 <Item arrow="horizontal" navigator={this.props.navigator} onClick={this._onClick.bind(this)} >
                    第一个标题
                 </Item>
                 <Item arrow="horizontal" onClick={()=>{}}>
                    第二个标题
                 </Item>
                 <Item arrow="horizontal" onClick={()=>{}}>
                    第三个标题
                 </Item>
                 <Item arrow="horizontal" onClick={()=>{}}>
                    第四个标题
                 </Item>
                 <Item arrow="horizontal" onClick={()=>{}}>
                    第五个标题
                 </Item>
    		 </List>
    		</View>
		)
	};
	/*
	 * 生命周期
	 */
	componentDidMount() {
		this._addBackAndroidListener(this.props.navigator);
	}

	componentWillUnmount() {
		this._removeBackAndroidListener();
	}

	//监听Android返回键
	_addBackAndroidListener(navigator) {
			if (Platform.OS === 'android') {
				var currTime = 0;
				BackAndroid.addEventListener('hardwareBackPress', () => {
					if (!navigator) {
						return false;
					}
					const routers = navigator.getCurrentRoutes();
					if (routers.length == 1) { //在主界面
						var nowTime = (new Date()).valueOf();
						if (nowTime - currTime > 2000) {
							currTime = nowTime;
							ToastAndroid.show("再按一次退出RN", ToastAndroid.SHORT);
							return true;
						}
						return false;
					} else { //在其他子页面
						navigator.pop();
						return true;
					}
				});
			}
		}
		//移除监听
	_removeBackAndroidListener() {
		if (Platform.OS === 'android') {
			BackAndroid.removeEventListener('hardwareBackPress');
		}
	}
}


const styles = StyleSheet.create({
	container: {
		flex: 1,
	},
	welcome: {
		fontSize: 20,
		textAlign: 'center',
		margin: 10,
	},
	instructions: {
		textAlign: 'center',
		color: '#333333',
		marginBottom: 5,
	}
});