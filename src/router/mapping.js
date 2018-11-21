const routeConfig = [{
    name: '根',
    path: '/',
    redirect: '/datapanel/',
    hidden: true
}, {
    name: '数据面板',
    path: '/datapanel/',
    redirect: '/datapanel/overview',
    component: resolve => {
        require.ensure(['components/datapanel-index.vue'], () => {
            resolve(require('components/datapanel-index.vue'));
        });
    },
    children: [{
        name: '总览',
        path: '/datapanel/overview',
        component: resolve => {
            require.ensure(['components/datapanel-overview.vue'], () => {
                resolve(require('components/datapanel-overview.vue'));
            });
        }
    }, {
        name: '产品线数据',
        path: '/datapanel/p4pOperatedata',
        component: resolve => {
            require.ensure(['components/datapanel-p4pOperatedata.vue'], () => {
                resolve(require('components/datapanel-p4pOperatedata.vue'))
            })
        }
    }, {
        name: '运营数据',
        path: '/datapanel/operation',
        component: resolve => {
            require.ensure(['components/datapanel-operation.vue'], () => {
                resolve(require('components/datapanel-operation.vue'));
            });
        }
    },  {
        name: 'P4P运营数据1',
        path: '/datapanel/p4pConsumption',
        component: resolve => {
            require.ensure(['components/datapanel-p4pConsumption.vue'], () => {
                resolve(require('components/datapanel-p4pConsumption.vue'));
            });
        },
        hidden: true
    },{
        name: '战斗力',
        path: '/datapanel/effectives',
        component: resolve => {
            require.ensure(['components/datapanel-effectives.vue'], () => {
                resolve(require('components/datapanel-effectives.vue'));
            });
        }
    }, {
        name: '付费会员',
        path: '/datapanel/member',
        component: resolve => {
            require.ensure(['components/datapanel-member.vue'], () => {
                resolve(require('components/datapanel-member.vue'));
            });
        }
    },

    /*{
        name: '运营数据',
        path: '/datapanel/operatedata',
        component: resolve => {
            require.ensure(['components/datapanel-operatedata.vue'], () => {
                resolve(require('components/datapanel-operatedata.vue'));
            });
        }
    }, */{
        name: '流量分布',
        path: '/datapanel/map',
        component: resolve => {
            require.ensure(['components/datapanel-map.vue'], () => {
                resolve(require('components/datapanel-map.vue'));
            });
        },
        hidden: true
    }, {
        name: '数据字典',
        path: '/datapanel/readme',
        component: resolve => {
            require.ensure(['components/datapanel-readme.vue'], () => {
                resolve(require('components/datapanel-readme.vue'));
            });
        }
    }, {
        name: '上传文件',
        path: '/datapanel/uploadexcel',
        component: resolve => {
            require.ensure(['components/datapanel-uploadexcel.vue'], () => {
                resolve(require('components/datapanel-uploadexcel.vue'));
            });
        }
    }, {
        name: '权限管理',
        path: '/datapanel/authority',
        component: resolve => {
            require.ensure(['components/datapanel-authority.vue'], () => {
                resolve(require('components/datapanel-authority.vue'));
            });
        }
    }
    ]
}];

export default routeConfig;
