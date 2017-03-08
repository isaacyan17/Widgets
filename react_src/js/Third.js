'use strict';
import React, {
  Component
} from 'react';
import {
  StyleSheet,
  Text,
  View
} from 'react-native';
import BaseTitleBar from '../component/BaseTitleBar';

export default class Third extends BaseTitleBar {

  constructor(props) {
    super(props);
  }

  static defaultProps = {
    titleText: 'test',
    titlePosition: 'left'
  }

  renderContent() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          我是 原生项目嵌入的 ReactNative3
        </Text>
        <Text style={styles.instructions}>
          Press Cmd+R to reload,{'\n'}
          Cmd+D or shake for dev menu
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
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
  },
});