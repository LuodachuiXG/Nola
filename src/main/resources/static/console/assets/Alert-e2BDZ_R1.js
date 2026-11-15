import{F as e}from"./apis-4W12woHh.js";import{At as t,Dt as n,Et as r,Fn as i,H as a,Mt as o,V as s,Y as c,at as l,c as u,cn as d,d as f,dn as p,en as m,hn as h,i as g,kt as _,o as v,ot as y,q as b,r as x,un as S,zn as C}from"./Wave-BaVmjZD-.js";import{v as w}from"./GlobalStore-sP_W_bFV.js";import{i as T,n as E,r as D,t as O}from"./Warning-BvYijV_Y.js";import{X as k,Z as A}from"./index-D6eSujYa.js";function j(e){let{lineHeight:t,borderRadius:n,fontWeightStrong:r,baseColor:i,dividerColor:a,actionColor:o,textColor1:s,textColor2:c,closeColorHover:u,closeColorPressed:d,closeIconColor:f,closeIconColorHover:p,closeIconColorPressed:m,infoColor:h,successColor:g,warningColor:_,errorColor:v,fontSize:b}=e;return Object.assign(Object.assign({},A),{fontSize:b,lineHeight:t,titleFontWeight:r,borderRadius:n,border:`1px solid ${a}`,color:o,titleTextColor:s,iconColor:c,contentTextColor:c,closeBorderRadius:n,closeColorHover:u,closeColorPressed:d,closeIconColor:f,closeIconColorHover:p,closeIconColorPressed:m,borderInfo:`1px solid ${y(i,l(h,{alpha:.25}))}`,colorInfo:y(i,l(h,{alpha:.08})),titleTextColorInfo:s,iconColorInfo:h,contentTextColorInfo:c,closeColorHoverInfo:u,closeColorPressedInfo:d,closeIconColorInfo:f,closeIconColorHoverInfo:p,closeIconColorPressedInfo:m,borderSuccess:`1px solid ${y(i,l(g,{alpha:.25}))}`,colorSuccess:y(i,l(g,{alpha:.08})),titleTextColorSuccess:s,iconColorSuccess:g,contentTextColorSuccess:c,closeColorHoverSuccess:u,closeColorPressedSuccess:d,closeIconColorSuccess:f,closeIconColorHoverSuccess:p,closeIconColorPressedSuccess:m,borderWarning:`1px solid ${y(i,l(_,{alpha:.33}))}`,colorWarning:y(i,l(_,{alpha:.08})),titleTextColorWarning:s,iconColorWarning:_,contentTextColorWarning:c,closeColorHoverWarning:u,closeColorPressedWarning:d,closeIconColorWarning:f,closeIconColorHoverWarning:p,closeIconColorPressedWarning:m,borderError:`1px solid ${y(i,l(v,{alpha:.25}))}`,colorError:y(i,l(v,{alpha:.08})),titleTextColorError:s,iconColorError:v,contentTextColorError:c,closeColorHoverError:u,closeColorPressedError:d,closeIconColorError:f,closeIconColorHoverError:p,closeIconColorPressedError:m})}var M={name:`Alert`,common:x,self:j},N=n(`alert`,`
 line-height: var(--n-line-height);
 border-radius: var(--n-border-radius);
 position: relative;
 transition: background-color .3s var(--n-bezier);
 background-color: var(--n-color);
 text-align: start;
 word-break: break-word;
`,[_(`border`,`
 border-radius: inherit;
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 transition: border-color .3s var(--n-bezier);
 border: var(--n-border);
 pointer-events: none;
 `),t(`closable`,[n(`alert-body`,[_(`title`,`
 padding-right: 24px;
 `)])]),_(`icon`,{color:`var(--n-icon-color)`}),n(`alert-body`,{padding:`var(--n-padding)`},[_(`title`,{color:`var(--n-title-text-color)`}),_(`content`,{color:`var(--n-content-text-color)`})]),k({originalTransition:`transform .3s var(--n-bezier)`,enterToProps:{transform:`scale(1)`},leaveToProps:{transform:`scale(0.9)`}}),_(`icon`,`
 position: absolute;
 left: 0;
 top: 0;
 align-items: center;
 justify-content: center;
 display: flex;
 width: var(--n-icon-size);
 height: var(--n-icon-size);
 font-size: var(--n-icon-size);
 margin: var(--n-icon-margin);
 `),_(`close`,`
 transition:
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
 position: absolute;
 right: 0;
 top: 0;
 margin: var(--n-close-margin);
 `),t(`show-icon`,[n(`alert-body`,{paddingLeft:`calc(var(--n-icon-margin-left) + var(--n-icon-size) + var(--n-icon-margin-right))`})]),t(`right-adjust`,[n(`alert-body`,{paddingRight:`calc(var(--n-close-size) + var(--n-padding) + 2px)`})]),n(`alert-body`,`
 border-radius: var(--n-border-radius);
 transition: border-color .3s var(--n-bezier);
 `,[_(`title`,`
 transition: color .3s var(--n-bezier);
 font-size: 16px;
 line-height: 19px;
 font-weight: var(--n-title-font-weight);
 `,[r(`& +`,[_(`content`,{marginTop:`9px`})])]),_(`content`,{transition:`color .3s var(--n-bezier)`,fontSize:`var(--n-font-size)`})]),_(`icon`,{transition:`color .3s var(--n-bezier)`})]);p(),i();var P=Object.assign(Object.assign({},u.props),{title:String,showIcon:{type:Boolean,default:!0},type:{type:String,default:`default`},bordered:{type:Boolean,default:!0},closable:Boolean,onClose:Function,onAfterLeave:Function,onAfterHide:Function}),F=d({name:`Alert`,inheritAttrs:!1,props:P,slots:Object,setup(t){let{mergedClsPrefixRef:n,mergedBorderedRef:r,inlineThemeDisabled:i,mergedRtlRef:c}=a(t),l=u(`Alert`,`-alert`,N,M,t,n),d=f(`Alert`,c,n),p=m(()=>{let{common:{cubicBezierEaseInOut:n},self:r}=l.value,{fontSize:i,borderRadius:a,titleFontWeight:s,lineHeight:c,iconSize:u,iconMargin:d,iconMarginRtl:f,closeIconSize:p,closeBorderRadius:m,closeSize:h,closeMargin:g,closeMarginRtl:_,padding:v}=r,{type:y}=t,{left:b,right:x}=e(d);return{"--n-bezier":n,"--n-color":r[o(`color`,y)],"--n-close-icon-size":p,"--n-close-border-radius":m,"--n-close-color-hover":r[o(`closeColorHover`,y)],"--n-close-color-pressed":r[o(`closeColorPressed`,y)],"--n-close-icon-color":r[o(`closeIconColor`,y)],"--n-close-icon-color-hover":r[o(`closeIconColorHover`,y)],"--n-close-icon-color-pressed":r[o(`closeIconColorPressed`,y)],"--n-icon-color":r[o(`iconColor`,y)],"--n-border":r[o(`border`,y)],"--n-title-text-color":r[o(`titleTextColor`,y)],"--n-content-text-color":r[o(`contentTextColor`,y)],"--n-line-height":c,"--n-border-radius":a,"--n-font-size":i,"--n-title-font-weight":s,"--n-icon-size":u,"--n-icon-margin":d,"--n-icon-margin-rtl":f,"--n-close-size":h,"--n-close-margin":g,"--n-close-margin-rtl":_,"--n-padding":v,"--n-icon-margin-left":b,"--n-icon-margin-right":x}}),h=i?s(`alert`,m(()=>t.type[0]),p,t):void 0,g=C(!0),_=()=>{let{onAfterLeave:e,onAfterHide:n}=t;e&&e(),n&&n()};return{rtlEnabled:d,mergedClsPrefix:n,mergedBordered:r,visible:g,handleCloseClick:()=>{Promise.resolve(t.onClose?.call(t)).then(e=>{e!==!1&&(g.value=!1)})},handleAfterLeave:()=>{_()},mergedTheme:l,cssVars:i?void 0:p,themeClass:h?.themeClass,onRender:h?.onRender}},render(){var e;return(e=this.onRender)==null||e.call(this),S(g,{onAfterLeave:this.handleAfterLeave},{default:()=>{let{mergedClsPrefix:e,$slots:t}=this,n={class:[`${e}-alert`,this.themeClass,this.closable&&`${e}-alert--closable`,this.showIcon&&`${e}-alert--show-icon`,!this.title&&this.closable&&`${e}-alert--right-adjust`,this.rtlEnabled&&`${e}-alert--rtl`],style:this.cssVars,role:`alert`};return this.visible?S(`div`,Object.assign({},h(this.$attrs,n)),this.closable&&S(w,{clsPrefix:e,class:`${e}-alert__close`,onClick:this.handleCloseClick}),this.bordered&&S(`div`,{class:`${e}-alert__border`}),this.showIcon&&S(`div`,{class:`${e}-alert__icon`,"aria-hidden":`true`},b(t.icon,()=>[S(v,{clsPrefix:e},{default:()=>{switch(this.type){case`success`:return S(E,null);case`info`:return S(D,null);case`warning`:return S(O,null);case`error`:return S(T,null);default:return null}}})])),S(`div`,{class:[`${e}-alert-body`,this.mergedBordered&&`${e}-alert-body--bordered`]},c(t.header,t=>{let n=t||this.title;return n?S(`div`,{class:`${e}-alert-body__title`},n):null}),t.default&&S(`div`,{class:`${e}-alert-body__content`},t))):null}})}});export{F as t};