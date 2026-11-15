import{At as e,Dt as t,Et as n,H as r,cn as i,d as a,dn as o,jt as s,kt as c,l,un as u,wn as d}from"./Wave-BaVmjZD-.js";import{a as f}from"./Button-CIcZXw0N.js";var p=`0!important`,m=`-1px!important`;function h(r){return e(`${r}-type`,[n(`& +`,[t(`button`,{},[e(`${r}-type`,[c(`border`,{borderLeftWidth:p}),c(`state-border`,{left:m})])])])])}function g(r){return e(`${r}-type`,[n(`& +`,[t(`button`,[e(`${r}-type`,[c(`border`,{borderTopWidth:p}),c(`state-border`,{top:m})])])])])}var _=t(`button-group`,`
 flex-wrap: nowrap;
 display: inline-flex;
 position: relative;
`,[s(`vertical`,{flexDirection:`row`},[s(`rtl`,[t(`button`,[n(`&:first-child:not(:last-child)`,`
 margin-right: ${p};
 border-top-right-radius: ${p};
 border-bottom-right-radius: ${p};
 `),n(`&:last-child:not(:first-child)`,`
 margin-left: ${p};
 border-top-left-radius: ${p};
 border-bottom-left-radius: ${p};
 `),n(`&:not(:first-child):not(:last-child)`,`
 margin-left: ${p};
 margin-right: ${p};
 border-radius: ${p};
 `),h(`default`),e(`ghost`,[h(`primary`),h(`info`),h(`success`),h(`warning`),h(`error`)])])])]),e(`vertical`,{flexDirection:`column`},[t(`button`,[n(`&:first-child:not(:last-child)`,`
 margin-bottom: ${p};
 margin-left: ${p};
 margin-right: ${p};
 border-bottom-left-radius: ${p};
 border-bottom-right-radius: ${p};
 `),n(`&:last-child:not(:first-child)`,`
 margin-top: ${p};
 margin-left: ${p};
 margin-right: ${p};
 border-top-left-radius: ${p};
 border-top-right-radius: ${p};
 `),n(`&:not(:first-child):not(:last-child)`,`
 margin: ${p};
 border-radius: ${p};
 `),g(`default`),e(`ghost`,[g(`primary`),g(`info`),g(`success`),g(`warning`),g(`error`)])])])]);o();var v=i({name:`ButtonGroup`,props:{size:{type:String,default:void 0},vertical:Boolean},setup(e){let{mergedClsPrefixRef:t,mergedRtlRef:n}=r(e);return l(`-button-group`,_,t),d(f,e),{rtlEnabled:a(`ButtonGroup`,n,t),mergedClsPrefix:t}},render(){let{mergedClsPrefix:e}=this;return u(`div`,{class:[`${e}-button-group`,this.rtlEnabled&&`${e}-button-group--rtl`,this.vertical&&`${e}-button-group--vertical`],role:`group`},this.$slots)}});export{v as t};