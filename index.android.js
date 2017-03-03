'use strict';
import React, {
  Component
} from 'react';
import {
  AppRegistry,
  StyleSheet,
  Platform
} from 'react-native';

import Settings from './react_src/js/setting';
import AppNavigator from './react_src/js/AppNavigator';

export default class Widgets extends Component {
  render() {
    return (
      //统一的入口
      <AppNavigator id='Settings' data='' name='' component={Settings} />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: 300,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
});

AppRegistry.registerComponent('reactactivity', () => Widgets);