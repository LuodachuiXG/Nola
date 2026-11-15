import{At as e,Dt as t,H as n,V as r,Xt as i,c as a,cn as o,dn as s,en as c,jt as l,kt as u,un as d}from"./Wave-BaVmjZD-.js";import{z as f}from"./index-D6eSujYa.js";var p=t(`divider`,`
 position: relative;
 display: flex;
 width: 100%;
 box-sizing: border-box;
 font-size: 16px;
 color: var(--n-text-color);
 transition:
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
`,[l(`vertical`,`
 margin-top: 24px;
 margin-bottom: 24px;
 `,[l(`no-title`,`
 display: flex;
 align-items: center;
 `)]),u(`title`,`
 display: flex;
 align-items: center;
 margin-left: 12px;
 margin-right: 12px;
 white-space: nowrap;
 font-weight: var(--n-font-weight);
 `),e(`title-position-left`,[u(`line`,[e(`left`,{width:`28px`})])]),e(`title-position-right`,[u(`line`,[e(`right`,{width:`28px`})])]),e(`dashed`,[u(`line`,`
 background-color: #0000;
 height: 0px;
 width: 100%;
 border-style: dashed;
 border-width: 1px 0 0;
 `)]),e(`vertical`,`
 display: inline-block;
 height: 1em;
 margin: 0 8px;
 vertical-align: middle;
 width: 1px;
 `),u(`line`,`
 border: none;
 transition: background-color .3s var(--n-bezier), border-color .3s var(--n-bezier);
 height: 1px;
 width: 100%;
 margin: 0;
 `),l(`dashed`,[u(`line`,{backgroundColor:`var(--n-color)`})]),e(`dashed`,[u(`line`,{borderColor:`var(--n-color)`})]),e(`vertical`,{backgroundColor:`var(--n-color)`})]);s();var m=Object.assign(Object.assign({},a.props),{titlePlacement:{type:String,default:`center`},dashed:Boolean,vertical:Boolean}),h=o({name:`Divider`,props:m,setup(e){let{mergedClsPrefixRef:t,inlineThemeDisabled:i}=n(e),o=a(`Divider`,`-divider`,p,f,e,t),s=c(()=>{let{common:{cubicBezierEaseInOut:e},self:{color:t,textColor:n,fontWeight:r}}=o.value;return{"--n-bezier":e,"--n-color":t,"--n-text-color":n,"--n-font-weight":r}}),l=i?r(`divider`,void 0,s,e):void 0;return{mergedClsPrefix:t,cssVars:i?void 0:s,themeClass:l?.themeClass,onRender:l?.onRender}},render(){var e;let{$slots:t,titlePlacement:n,vertical:r,dashed:a,cssVars:o,mergedClsPrefix:s}=this;return(e=this.onRender)==null||e.call(this),d(`div`,{role:`separator`,class:[`${s}-divider`,this.themeClass,{[`${s}-divider--vertical`]:r,[`${s}-divider--no-title`]:!t.default,[`${s}-divider--dashed`]:a,[`${s}-divider--title-position-${n}`]:t.default&&n}],style:o},r?null:d(`div`,{class:`${s}-divider__line ${s}-divider__line--left`}),!r&&t.default?d(i,null,d(`div`,{class:`${s}-divider__title`},this.$slots),d(`div`,{class:`${s}-divider__line ${s}-divider__line--right`})):null)}});export{h as t};