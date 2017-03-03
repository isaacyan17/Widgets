'use strict';
import React, {
	Component
} from 'react';
import {
	AppRegistry,
	StyleSheet,
	Text,
	View
} from 'react-native';
import {
	Button,
	List
} from 'antd-mobile';

import Index from './Third';

const Item = List.Item;
const Brief = Item.Brief;

export default class Settings extends Component {
	constructor(props) {
		super(props);
	}

	_onClick() {
		//测试点击navigator的点击跳转
		//方法名和点击事件名一致....
		const navigator = this.props.navigator;
		if (navigator) {
			navigator.push({
				id: 'thirdpage',
				data: '',
				name: 'third',
				component: Index
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