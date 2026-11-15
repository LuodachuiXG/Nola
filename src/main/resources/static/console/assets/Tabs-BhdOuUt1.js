import{$t as e,At as t,B as n,Dt as r,Et as i,Fn as a,H as o,Hn as s,Ht as c,Kt as l,Mn as u,Mt as d,N as f,Pn as p,Q as m,V as h,Wt as g,X as _,Xt as v,Y as y,c as b,cn as x,dn as S,en as C,fn as w,gn as T,hn as E,jn as D,jt as O,kt as k,o as A,rt as j,tt as M,un as N,wn as ee,xn as te,zn as P}from"./Wave-BaVmjZD-.js";import{n as F}from"./MyNumberAnimation-CKG8j0tO.js";import{F as I,N as ne,k as L}from"./apis-4W12woHh.js";import{G as R,I as z,K as re,L as ie,rt as ae}from"./Modal-SNnNCmng.js";import{S as oe,b as B,v as se}from"./GlobalStore-sP_W_bFV.js";import{y as ce}from"./Input-uWJcXTtk.js";import{R as le}from"./index-D6eSujYa.js";import{t as V}from"./Add-iEI0_HWY.js";S(),a();var H=R(`.v-x-scroll`,{overflow:`auto`,scrollbarWidth:`none`},[R(`&::-webkit-scrollbar`,{width:0,height:0})]),ue=x({name:`XScroll`,props:{disabled:Boolean,onScroll:Function},setup(){let e=P(null);function t(e){e.currentTarget.offsetWidth<e.currentTarget.scrollWidth&&e.deltaY!==0&&(e.currentTarget.scrollLeft+=e.deltaY+e.deltaX,e.preventDefault())}let n=M();return H.mount({id:`vueuc/x-scroll`,head:!0,anchorMetaName:re,ssr:n}),Object.assign({selfRef:e,handleWheel:t},{scrollTo(...t){var n;(n=e.value)==null||n.scrollTo(...t)}})},render(){return N(`div`,{ref:`selfRef`,onScroll:this.onScroll,onWheel:this.disabled?void 0:this.handleWheel,class:`v-x-scroll`},this.$slots)}}),U=function(){return n.Date.now()},de=`Expected a function`,W=Math.max,G=Math.min;function K(e,t,n){var r,i,a,o,s,c,l=0,u=!1,d=!1,p=!0;if(typeof e!=`function`)throw TypeError(de);t=F(t)||0,f(n)&&(u=!!n.leading,d=`maxWait`in n,a=d?W(F(n.maxWait)||0,t):a,p=`trailing`in n?!!n.trailing:p);function m(t){var n=r,a=i;return r=i=void 0,l=t,o=e.apply(a,n),o}function h(e){return l=e,s=setTimeout(v,t),u?m(e):o}function g(e){var n=e-c,r=e-l,i=t-n;return d?G(i,a-r):i}function _(e){var n=e-c,r=e-l;return c===void 0||n>=t||n<0||d&&r>=a}function v(){var e=U();if(_(e))return y(e);s=setTimeout(v,g(e))}function y(e){return s=void 0,p&&r?m(e):(r=i=void 0,o)}function b(){s!==void 0&&clearTimeout(s),l=0,r=c=i=s=void 0}function x(){return s===void 0?o:y(U())}function S(){var e=U(),n=_(e);if(r=arguments,i=this,c=e,n){if(s===void 0)return h(c);if(d)return clearTimeout(s),s=setTimeout(v,t),m(c)}return s===void 0&&(s=setTimeout(v,t)),o}return S.cancel=b,S.flush=x,S}var q=`Expected a function`;function J(e,t,n){var r=!0,i=!0;if(typeof e!=`function`)throw TypeError(q);return f(n)&&(r=`leading`in n?!!n.leading:r,i=`trailing`in n?!!n.trailing:i),K(e,t,{leading:r,maxWait:t,trailing:i})}var Y=j(`n-tabs`);S();var X={tab:[String,Number,Object,Function],name:{type:[String,Number],required:!0},disabled:Boolean,displayDirective:{type:String,default:`if`},closable:{type:Boolean,default:void 0},tabProps:Object,label:[String,Number,Object,Function]},fe=x({__TAB_PANE__:!0,name:`TabPane`,alias:[`TabPanel`],props:X,slots:Object,setup(e){let t=w(Y,null);return t||m(`tab-pane`,"`n-tab-pane` must be placed inside `n-tabs`."),{style:t.paneStyleRef,class:t.paneClassRef,mergedClsPrefix:t.mergedClsPrefixRef}},render(){return N(`div`,{class:[`${this.mergedClsPrefix}-tab-pane`,this.class],style:this.style},this.$slots)}});S();var pe=Object.assign({internalLeftPadded:Boolean,internalAddable:Boolean,internalCreatedByPane:Boolean},ie(X,[`displayDirective`])),Z=x({__TAB__:!0,inheritAttrs:!1,name:`Tab`,props:pe,setup(e){let{mergedClsPrefixRef:t,valueRef:n,typeRef:r,closableRef:i,tabStyleRef:a,addTabStyleRef:o,tabClassRef:s,addTabClassRef:c,tabChangeIdRef:l,onBeforeLeaveRef:u,triggerRef:d,handleAdd:f,activateTab:p,handleClose:m}=w(Y);return{trigger:d,mergedClosable:C(()=>{if(e.internalAddable)return!1;let{closable:t}=e;return t===void 0?i.value:t}),style:a,addStyle:o,tabClass:s,addTabClass:c,clsPrefix:t,value:n,type:r,handleClose(t){t.stopPropagation(),e.disabled||m(e.name)},activateTab(){if(e.disabled)return;if(e.internalAddable){f();return}let{name:t}=e,r=++l.id;if(t!==n.value){let{value:i}=u;i?Promise.resolve(i(e.name,n.value)).then(e=>{e&&l.id===r&&p(t)}):p(t)}}}},render(){let{internalAddable:e,clsPrefix:t,name:n,disabled:r,label:i,tab:a,value:o,mergedClosable:s,trigger:c,$slots:{default:l}}=this,u=i??a;return N(`div`,{class:`${t}-tabs-tab-wrapper`},this.internalLeftPadded?N(`div`,{class:`${t}-tabs-tab-pad`}):null,N(`div`,Object.assign({key:n,"data-name":n,"data-disabled":r?!0:void 0},E({class:[`${t}-tabs-tab`,o===n&&`${t}-tabs-tab--active`,r&&`${t}-tabs-tab--disabled`,s&&`${t}-tabs-tab--closable`,e&&`${t}-tabs-tab--addable`,e?this.addTabClass:this.tabClass],onClick:c===`click`?this.activateTab:void 0,onMouseenter:c===`hover`?this.activateTab:void 0,style:e?this.addStyle:this.style},this.internalCreatedByPane?this.tabProps||{}:this.$attrs)),N(`span`,{class:`${t}-tabs-tab__label`},e?N(v,null,N(`div`,{class:`${t}-tabs-tab__height-placeholder`},`\xA0`),N(A,{clsPrefix:t},{default:()=>N(V,null)})):l?l():typeof u==`object`?u:z(u??n)),s&&this.type===`card`?N(se,{clsPrefix:t,class:`${t}-tabs-tab__close`,onClick:this.handleClose,disabled:r}):null))}}),me=r(`tabs`,`
 box-sizing: border-box;
 width: 100%;
 display: flex;
 flex-direction: column;
 transition:
 background-color .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
`,[t(`segment-type`,[r(`tabs-rail`,[i(`&.transition-disabled`,[r(`tabs-capsule`,`
 transition: none;
 `)])])]),t(`top`,[r(`tab-pane`,`
 padding: var(--n-pane-padding-top) var(--n-pane-padding-right) var(--n-pane-padding-bottom) var(--n-pane-padding-left);
 `)]),t(`left`,[r(`tab-pane`,`
 padding: var(--n-pane-padding-right) var(--n-pane-padding-bottom) var(--n-pane-padding-left) var(--n-pane-padding-top);
 `)]),t(`left, right`,`
 flex-direction: row;
 `,[r(`tabs-bar`,`
 width: 2px;
 right: 0;
 transition:
 top .2s var(--n-bezier),
 max-height .2s var(--n-bezier),
 background-color .3s var(--n-bezier);
 `),r(`tabs-tab`,`
 padding: var(--n-tab-padding-vertical); 
 `)]),t(`right`,`
 flex-direction: row-reverse;
 `,[r(`tab-pane`,`
 padding: var(--n-pane-padding-left) var(--n-pane-padding-top) var(--n-pane-padding-right) var(--n-pane-padding-bottom);
 `),r(`tabs-bar`,`
 left: 0;
 `)]),t(`bottom`,`
 flex-direction: column-reverse;
 justify-content: flex-end;
 `,[r(`tab-pane`,`
 padding: var(--n-pane-padding-bottom) var(--n-pane-padding-right) var(--n-pane-padding-top) var(--n-pane-padding-left);
 `),r(`tabs-bar`,`
 top: 0;
 `)]),r(`tabs-rail`,`
 position: relative;
 padding: 3px;
 border-radius: var(--n-tab-border-radius);
 width: 100%;
 background-color: var(--n-color-segment);
 transition: background-color .3s var(--n-bezier);
 display: flex;
 align-items: center;
 `,[r(`tabs-capsule`,`
 border-radius: var(--n-tab-border-radius);
 position: absolute;
 pointer-events: none;
 background-color: var(--n-tab-color-segment);
 box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .08);
 transition: transform 0.3s var(--n-bezier);
 `),r(`tabs-tab-wrapper`,`
 flex-basis: 0;
 flex-grow: 1;
 display: flex;
 align-items: center;
 justify-content: center;
 `,[r(`tabs-tab`,`
 overflow: hidden;
 border-radius: var(--n-tab-border-radius);
 width: 100%;
 display: flex;
 align-items: center;
 justify-content: center;
 `,[t(`active`,`
 font-weight: var(--n-font-weight-strong);
 color: var(--n-tab-text-color-active);
 `),i(`&:hover`,`
 color: var(--n-tab-text-color-hover);
 `)])])]),t(`flex`,[r(`tabs-nav`,`
 width: 100%;
 position: relative;
 `,[r(`tabs-wrapper`,`
 width: 100%;
 `,[r(`tabs-tab`,`
 margin-right: 0;
 `)])])]),r(`tabs-nav`,`
 box-sizing: border-box;
 line-height: 1.5;
 display: flex;
 transition: border-color .3s var(--n-bezier);
 `,[k(`prefix, suffix`,`
 display: flex;
 align-items: center;
 `),k(`prefix`,`padding-right: 16px;`),k(`suffix`,`padding-left: 16px;`)]),t(`top, bottom`,[r(`tabs-nav-scroll-wrapper`,[i(`&::before`,`
 top: 0;
 bottom: 0;
 left: 0;
 width: 20px;
 `),i(`&::after`,`
 top: 0;
 bottom: 0;
 right: 0;
 width: 20px;
 `),t(`shadow-start`,[i(`&::before`,`
 box-shadow: inset 10px 0 8px -8px rgba(0, 0, 0, .12);
 `)]),t(`shadow-end`,[i(`&::after`,`
 box-shadow: inset -10px 0 8px -8px rgba(0, 0, 0, .12);
 `)])])]),t(`left, right`,[r(`tabs-nav-scroll-content`,`
 flex-direction: column;
 `),r(`tabs-nav-scroll-wrapper`,[i(`&::before`,`
 top: 0;
 left: 0;
 right: 0;
 height: 20px;
 `),i(`&::after`,`
 bottom: 0;
 left: 0;
 right: 0;
 height: 20px;
 `),t(`shadow-start`,[i(`&::before`,`
 box-shadow: inset 0 10px 8px -8px rgba(0, 0, 0, .12);
 `)]),t(`shadow-end`,[i(`&::after`,`
 box-shadow: inset 0 -10px 8px -8px rgba(0, 0, 0, .12);
 `)])])]),r(`tabs-nav-scroll-wrapper`,`
 flex: 1;
 position: relative;
 overflow: hidden;
 `,[r(`tabs-nav-y-scroll`,`
 height: 100%;
 width: 100%;
 overflow-y: auto; 
 scrollbar-width: none;
 `,[i(`&::-webkit-scrollbar, &::-webkit-scrollbar-track-piece, &::-webkit-scrollbar-thumb`,`
 width: 0;
 height: 0;
 display: none;
 `)]),i(`&::before, &::after`,`
 transition: box-shadow .3s var(--n-bezier);
 pointer-events: none;
 content: "";
 position: absolute;
 z-index: 1;
 `)]),r(`tabs-nav-scroll-content`,`
 display: flex;
 position: relative;
 min-width: 100%;
 min-height: 100%;
 width: fit-content;
 box-sizing: border-box;
 `),r(`tabs-wrapper`,`
 display: inline-flex;
 flex-wrap: nowrap;
 position: relative;
 `),r(`tabs-tab-wrapper`,`
 display: flex;
 flex-wrap: nowrap;
 flex-shrink: 0;
 flex-grow: 0;
 `),r(`tabs-tab`,`
 cursor: pointer;
 white-space: nowrap;
 flex-wrap: nowrap;
 display: inline-flex;
 align-items: center;
 color: var(--n-tab-text-color);
 font-size: var(--n-tab-font-size);
 background-clip: padding-box;
 padding: var(--n-tab-padding);
 transition:
 box-shadow .3s var(--n-bezier),
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 `,[t(`disabled`,{cursor:`not-allowed`}),k(`close`,`
 margin-left: 6px;
 transition:
 background-color .3s var(--n-bezier),
 color .3s var(--n-bezier);
 `),k(`label`,`
 display: flex;
 align-items: center;
 z-index: 1;
 `)]),r(`tabs-bar`,`
 position: absolute;
 bottom: 0;
 height: 2px;
 border-radius: 1px;
 background-color: var(--n-bar-color);
 transition:
 left .2s var(--n-bezier),
 max-width .2s var(--n-bezier),
 opacity .3s var(--n-bezier),
 background-color .3s var(--n-bezier);
 `,[i(`&.transition-disabled`,`
 transition: none;
 `),t(`disabled`,`
 background-color: var(--n-tab-text-color-disabled)
 `)]),r(`tabs-pane-wrapper`,`
 position: relative;
 overflow: hidden;
 transition: max-height .2s var(--n-bezier);
 `),r(`tab-pane`,`
 color: var(--n-pane-text-color);
 width: 100%;
 transition:
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 opacity .2s var(--n-bezier);
 left: 0;
 right: 0;
 top: 0;
 `,[i(`&.next-transition-leave-active, &.prev-transition-leave-active, &.next-transition-enter-active, &.prev-transition-enter-active`,`
 transition:
 color .3s var(--n-bezier),
 background-color .3s var(--n-bezier),
 transform .2s var(--n-bezier),
 opacity .2s var(--n-bezier);
 `),i(`&.next-transition-leave-active, &.prev-transition-leave-active`,`
 position: absolute;
 `),i(`&.next-transition-enter-from, &.prev-transition-leave-to`,`
 transform: translateX(32px);
 opacity: 0;
 `),i(`&.next-transition-leave-to, &.prev-transition-enter-from`,`
 transform: translateX(-32px);
 opacity: 0;
 `),i(`&.next-transition-leave-from, &.next-transition-enter-to, &.prev-transition-leave-from, &.prev-transition-enter-to`,`
 transform: translateX(0);
 opacity: 1;
 `)]),r(`tabs-tab-pad`,`
 box-sizing: border-box;
 width: var(--n-tab-gap);
 flex-grow: 0;
 flex-shrink: 0;
 `),t(`line-type, bar-type`,[r(`tabs-tab`,`
 font-weight: var(--n-tab-font-weight);
 box-sizing: border-box;
 vertical-align: bottom;
 `,[i(`&:hover`,{color:`var(--n-tab-text-color-hover)`}),t(`active`,`
 color: var(--n-tab-text-color-active);
 font-weight: var(--n-tab-font-weight-active);
 `),t(`disabled`,{color:`var(--n-tab-text-color-disabled)`})])]),r(`tabs-nav`,[t(`line-type`,[t(`top`,[k(`prefix, suffix`,`
 border-bottom: 1px solid var(--n-tab-border-color);
 `),r(`tabs-nav-scroll-content`,`
 border-bottom: 1px solid var(--n-tab-border-color);
 `),r(`tabs-bar`,`
 bottom: -1px;
 `)]),t(`left`,[k(`prefix, suffix`,`
 border-right: 1px solid var(--n-tab-border-color);
 `),r(`tabs-nav-scroll-content`,`
 border-right: 1px solid var(--n-tab-border-color);
 `),r(`tabs-bar`,`
 right: -1px;
 `)]),t(`right`,[k(`prefix, suffix`,`
 border-left: 1px solid var(--n-tab-border-color);
 `),r(`tabs-nav-scroll-content`,`
 border-left: 1px solid var(--n-tab-border-color);
 `),r(`tabs-bar`,`
 left: -1px;
 `)]),t(`bottom`,[k(`prefix, suffix`,`
 border-top: 1px solid var(--n-tab-border-color);
 `),r(`tabs-nav-scroll-content`,`
 border-top: 1px solid var(--n-tab-border-color);
 `),r(`tabs-bar`,`
 top: -1px;
 `)]),k(`prefix, suffix`,`
 transition: border-color .3s var(--n-bezier);
 `),r(`tabs-nav-scroll-content`,`
 transition: border-color .3s var(--n-bezier);
 `),r(`tabs-bar`,`
 border-radius: 0;
 `)]),t(`card-type`,[k(`prefix, suffix`,`
 transition: border-color .3s var(--n-bezier);
 `),r(`tabs-pad`,`
 flex-grow: 1;
 transition: border-color .3s var(--n-bezier);
 `),r(`tabs-tab-pad`,`
 transition: border-color .3s var(--n-bezier);
 `),r(`tabs-tab`,`
 font-weight: var(--n-tab-font-weight);
 border: 1px solid var(--n-tab-border-color);
 background-color: var(--n-tab-color);
 box-sizing: border-box;
 position: relative;
 vertical-align: bottom;
 display: flex;
 justify-content: space-between;
 font-size: var(--n-tab-font-size);
 color: var(--n-tab-text-color);
 `,[t(`addable`,`
 padding-left: 8px;
 padding-right: 8px;
 font-size: 16px;
 justify-content: center;
 `,[k(`height-placeholder`,`
 width: 0;
 font-size: var(--n-tab-font-size);
 `),O(`disabled`,[i(`&:hover`,`
 color: var(--n-tab-text-color-hover);
 `)])]),t(`closable`,`padding-right: 8px;`),t(`active`,`
 background-color: #0000;
 font-weight: var(--n-tab-font-weight-active);
 color: var(--n-tab-text-color-active);
 `),t(`disabled`,`color: var(--n-tab-text-color-disabled);`)])]),t(`left, right`,`
 flex-direction: column; 
 `,[k(`prefix, suffix`,`
 padding: var(--n-tab-padding-vertical);
 `),r(`tabs-wrapper`,`
 flex-direction: column;
 `),r(`tabs-tab-wrapper`,`
 flex-direction: column;
 `,[r(`tabs-tab-pad`,`
 height: var(--n-tab-gap-vertical);
 width: 100%;
 `)])]),t(`top`,[t(`card-type`,[r(`tabs-scroll-padding`,`border-bottom: 1px solid var(--n-tab-border-color);`),k(`prefix, suffix`,`
 border-bottom: 1px solid var(--n-tab-border-color);
 `),r(`tabs-tab`,`
 border-top-left-radius: var(--n-tab-border-radius);
 border-top-right-radius: var(--n-tab-border-radius);
 `,[t(`active`,`
 border-bottom: 1px solid #0000;
 `)]),r(`tabs-tab-pad`,`
 border-bottom: 1px solid var(--n-tab-border-color);
 `),r(`tabs-pad`,`
 border-bottom: 1px solid var(--n-tab-border-color);
 `)])]),t(`left`,[t(`card-type`,[r(`tabs-scroll-padding`,`border-right: 1px solid var(--n-tab-border-color);`),k(`prefix, suffix`,`
 border-right: 1px solid var(--n-tab-border-color);
 `),r(`tabs-tab`,`
 border-top-left-radius: var(--n-tab-border-radius);
 border-bottom-left-radius: var(--n-tab-border-radius);
 `,[t(`active`,`
 border-right: 1px solid #0000;
 `)]),r(`tabs-tab-pad`,`
 border-right: 1px solid var(--n-tab-border-color);
 `),r(`tabs-pad`,`
 border-right: 1px solid var(--n-tab-border-color);
 `)])]),t(`right`,[t(`card-type`,[r(`tabs-scroll-padding`,`border-left: 1px solid var(--n-tab-border-color);`),k(`prefix, suffix`,`
 border-left: 1px solid var(--n-tab-border-color);
 `),r(`tabs-tab`,`
 border-top-right-radius: var(--n-tab-border-radius);
 border-bottom-right-radius: var(--n-tab-border-radius);
 `,[t(`active`,`
 border-left: 1px solid #0000;
 `)]),r(`tabs-tab-pad`,`
 border-left: 1px solid var(--n-tab-border-color);
 `),r(`tabs-pad`,`
 border-left: 1px solid var(--n-tab-border-color);
 `)])]),t(`bottom`,[t(`card-type`,[r(`tabs-scroll-padding`,`border-top: 1px solid var(--n-tab-border-color);`),k(`prefix, suffix`,`
 border-top: 1px solid var(--n-tab-border-color);
 `),r(`tabs-tab`,`
 border-bottom-left-radius: var(--n-tab-border-radius);
 border-bottom-right-radius: var(--n-tab-border-radius);
 `,[t(`active`,`
 border-top: 1px solid #0000;
 `)]),r(`tabs-tab-pad`,`
 border-top: 1px solid var(--n-tab-border-color);
 `),r(`tabs-pad`,`
 border-top: 1px solid var(--n-tab-border-color);
 `)])])])]);S(),a(),g();var he=Object.assign(Object.assign({},b.props),{value:[String,Number],defaultValue:[String,Number],trigger:{type:String,default:`click`},type:{type:String,default:`bar`},closable:Boolean,justifyContent:String,size:{type:String,default:`medium`},placement:{type:String,default:`top`},tabStyle:[String,Object],tabClass:String,addTabStyle:[String,Object],addTabClass:String,barWidth:Number,paneClass:String,paneStyle:[String,Object],paneWrapperClass:String,paneWrapperStyle:[String,Object],addable:[Boolean,Object],tabsPadding:{type:Number,default:0},animated:Boolean,onBeforeLeave:Function,onAdd:Function,"onUpdate:value":[Function,Array],onUpdateValue:[Function,Array],onClose:[Function,Array],labelSize:String,activeName:[String,Number],onActiveNameChange:[Function,Array]}),ge=x({name:`Tabs`,props:he,slots:Object,setup(e,{slots:t}){let{mergedClsPrefixRef:n,inlineThemeDisabled:r}=o(e),i=b(`Tabs`,`-tabs`,me,le,e,n),a=P(null),c=P(null),l=P(null),f=P(null),p=P(null),m=P(null),g=P(!0),v=P(!0),y=oe(e,[`labelSize`,`size`]),x=oe(e,[`activeName`,`value`]),S=P(x.value??e.defaultValue??(t.default?B(t.default())[0]?.props?.name:null)),w=ce(x,S),E={id:0},O=C(()=>{if(e.justifyContent&&e.type!==`card`)return{display:`flex`,justifyContent:e.justifyContent}});D(w,()=>{E.id=0,N(),F()});function k(){let{value:e}=w;return e===null?null:a.value?.querySelector(`[data-name="${e}"]`)}function A(t){if(e.type===`card`)return;let{value:r}=c;if(!r)return;let i=r.style.opacity===`0`;if(t){let a=`${n.value}-tabs-bar--disabled`,{barWidth:o,placement:s}=e;if(t.dataset.disabled===`true`?r.classList.add(a):r.classList.remove(a),[`top`,`bottom`].includes(s)){if(M([`top`,`maxHeight`,`height`]),typeof o==`number`&&t.offsetWidth>=o){let e=Math.floor((t.offsetWidth-o)/2)+t.offsetLeft;r.style.left=`${e}px`,r.style.maxWidth=`${o}px`}else r.style.left=`${t.offsetLeft}px`,r.style.maxWidth=`${t.offsetWidth}px`;r.style.width=`8192px`,i&&(r.style.transition=`none`),r.offsetWidth,i&&(r.style.transition=``,r.style.opacity=`1`)}else{if(M([`left`,`maxWidth`,`width`]),typeof o==`number`&&t.offsetHeight>=o){let e=Math.floor((t.offsetHeight-o)/2)+t.offsetTop;r.style.top=`${e}px`,r.style.maxHeight=`${o}px`}else r.style.top=`${t.offsetTop}px`,r.style.maxHeight=`${t.offsetHeight}px`;r.style.height=`8192px`,i&&(r.style.transition=`none`),r.offsetHeight,i&&(r.style.transition=``,r.style.opacity=`1`)}}}function j(){if(e.type===`card`)return;let{value:t}=c;t&&(t.style.opacity=`0`)}function M(e){let{value:t}=c;if(t)for(let n of e)t.style[n]=``}function N(){if(e.type===`card`)return;let t=k();t?A(t):j()}function F(){let e=p.value?.$el;if(!e)return;let t=k();if(!t)return;let{scrollLeft:n,offsetWidth:r}=e,{offsetLeft:i,offsetWidth:a}=t;n>i?e.scrollTo({top:0,left:i,behavior:`smooth`}):i+a>n+r&&e.scrollTo({top:0,left:i+a-r,behavior:`smooth`})}let L=P(null),R=0,z=null;function re(e){let t=L.value;if(t){R=e.getBoundingClientRect().height;let n=`${R}px`,r=()=>{t.style.height=n,t.style.maxHeight=n};z?(r(),z(),z=null):z=r}}function ie(e){let t=L.value;if(t){let n=e.getBoundingClientRect().height,r=()=>{document.body.offsetHeight,t.style.maxHeight=`${n}px`,t.style.height=`${Math.max(R,n)}px`};z?(z(),z=null,r()):z=r}}function se(){let t=L.value;if(t){t.style.maxHeight=``,t.style.height=``;let{paneWrapperStyle:n}=e;if(typeof n==`string`)t.style.cssText=n;else if(n){let{maxHeight:e,height:r}=n;e!==void 0&&(t.style.maxHeight=e),r!==void 0&&(t.style.height=r)}}}let V={value:[]},H=P(`next`);function ue(e){let t=w.value,n=`next`;for(let r of V.value){if(r===t)break;if(r===e){n=`prev`;break}}H.value=n,U(e)}function U(t){let{onActiveNameChange:n,onUpdateValue:r,"onUpdate:value":i}=e;n&&_(n,t),r&&_(r,t),i&&_(i,t),S.value=t}function de(t){let{onClose:n}=e;n&&_(n,t)}let W=!0;function G(){let{value:e}=c;if(!e)return;W||=!1;let t=`transition-disabled`;e.classList.add(t),N(),e.classList.remove(t)}let K=P(null);function q({transitionDisabled:e}){let t=a.value;if(!t)return;e&&t.classList.add(`transition-disabled`);let n=k();n&&K.value&&(K.value.style.width=`${n.offsetWidth}px`,K.value.style.height=`${n.offsetHeight}px`,K.value.style.transform=`translateX(${n.offsetLeft-ne(getComputedStyle(t).paddingLeft)}px)`,e&&K.value.offsetWidth),e&&t.classList.remove(`transition-disabled`)}D([w],()=>{e.type===`segment`&&T(()=>{q({transitionDisabled:!1})})}),te(()=>{e.type===`segment`&&q({transitionDisabled:!0})});let X=0;function fe(t){if(t.contentRect.width===0&&t.contentRect.height===0||X===t.contentRect.width)return;X=t.contentRect.width;let{type:n}=e;if((n===`line`||n===`bar`)&&(W||e.justifyContent?.startsWith(`space`))&&G(),n!==`segment`){let{placement:t}=e;$((t===`top`||t===`bottom`?p.value?.$el:m.value)||null)}}let pe=J(fe,64);D([()=>e.justifyContent,()=>e.size],()=>{T(()=>{let{type:t}=e;(t===`line`||t===`bar`)&&G()})});let Z=P(!1);function he(t){let{target:n,contentRect:{width:r,height:i}}=t,a=n.parentElement.parentElement.offsetWidth,o=n.parentElement.parentElement.offsetHeight,{placement:s}=e;if(!Z.value)s===`top`||s===`bottom`?a<r&&(Z.value=!0):o<i&&(Z.value=!0);else{let{value:e}=f;if(!e)return;s===`top`||s===`bottom`?a-r>e.$el.offsetWidth&&(Z.value=!1):o-i>e.$el.offsetHeight&&(Z.value=!1)}$(p.value?.$el||null)}let ge=J(he,64);function Q(){let{onAdd:t}=e;t&&t(),T(()=>{let e=k(),{value:t}=p;e&&t&&t.scrollTo({left:e.offsetLeft,top:0,behavior:`smooth`})})}function $(t){if(!t)return;let{placement:n}=e;if(n===`top`||n===`bottom`){let{scrollLeft:e,scrollWidth:n,offsetWidth:r}=t;g.value=e<=0,v.value=e+r>=n}else{let{scrollTop:e,scrollHeight:n,offsetHeight:r}=t;g.value=e<=0,v.value=e+r>=n}}let _e=J(e=>{$(e.target)},64);ee(Y,{triggerRef:s(e,`trigger`),tabStyleRef:s(e,`tabStyle`),tabClassRef:s(e,`tabClass`),addTabStyleRef:s(e,`addTabStyle`),addTabClassRef:s(e,`addTabClass`),paneClassRef:s(e,`paneClass`),paneStyleRef:s(e,`paneStyle`),mergedClsPrefixRef:n,typeRef:s(e,`type`),closableRef:s(e,`closable`),valueRef:w,tabChangeIdRef:E,onBeforeLeaveRef:s(e,`onBeforeLeave`),activateTab:ue,handleClose:de,handleAdd:Q}),ae(()=>{N(),F()}),u(()=>{let{value:e}=l;if(!e)return;let{value:t}=n,r=`${t}-tabs-nav-scroll-wrapper--shadow-start`,i=`${t}-tabs-nav-scroll-wrapper--shadow-end`;g.value?e.classList.remove(r):e.classList.add(r),v.value?e.classList.remove(i):e.classList.add(i)});let ve={syncBarPosition:()=>{N()}},ye=()=>{q({transitionDisabled:!0})},be=C(()=>{let{value:t}=y,{type:n}=e,r=`${t}${{card:`Card`,bar:`Bar`,line:`Line`,segment:`Segment`}[n]}`,{self:{barColor:a,closeIconColor:o,closeIconColorHover:s,closeIconColorPressed:c,tabColor:l,tabBorderColor:u,paneTextColor:f,tabFontWeight:p,tabBorderRadius:m,tabFontWeightActive:h,colorSegment:g,fontWeightStrong:_,tabColorSegment:v,closeSize:b,closeIconSize:x,closeColorHover:S,closeColorPressed:C,closeBorderRadius:w,[d(`panePadding`,t)]:T,[d(`tabPadding`,r)]:E,[d(`tabPaddingVertical`,r)]:D,[d(`tabGap`,r)]:O,[d(`tabGap`,`${r}Vertical`)]:k,[d(`tabTextColor`,n)]:A,[d(`tabTextColorActive`,n)]:j,[d(`tabTextColorHover`,n)]:M,[d(`tabTextColorDisabled`,n)]:N,[d(`tabFontSize`,t)]:ee},common:{cubicBezierEaseInOut:te}}=i.value;return{"--n-bezier":te,"--n-color-segment":g,"--n-bar-color":a,"--n-tab-font-size":ee,"--n-tab-text-color":A,"--n-tab-text-color-active":j,"--n-tab-text-color-disabled":N,"--n-tab-text-color-hover":M,"--n-pane-text-color":f,"--n-tab-border-color":u,"--n-tab-border-radius":m,"--n-close-size":b,"--n-close-icon-size":x,"--n-close-color-hover":S,"--n-close-color-pressed":C,"--n-close-border-radius":w,"--n-close-icon-color":o,"--n-close-icon-color-hover":s,"--n-close-icon-color-pressed":c,"--n-tab-color":l,"--n-tab-font-weight":p,"--n-tab-font-weight-active":h,"--n-tab-padding":E,"--n-tab-padding-vertical":D,"--n-tab-gap":O,"--n-tab-gap-vertical":k,"--n-pane-padding-left":I(T,`left`),"--n-pane-padding-right":I(T,`right`),"--n-pane-padding-top":I(T,`top`),"--n-pane-padding-bottom":I(T,`bottom`),"--n-font-weight-strong":_,"--n-tab-color-segment":v}}),xe=r?h(`tabs`,C(()=>`${y.value[0]}${e.type[0]}`),be,e):void 0;return Object.assign({mergedClsPrefix:n,mergedValue:w,renderedNames:new Set,segmentCapsuleElRef:K,tabsPaneWrapperRef:L,tabsElRef:a,barElRef:c,addTabInstRef:f,xScrollInstRef:p,scrollWrapperElRef:l,addTabFixed:Z,tabWrapperStyle:O,handleNavResize:pe,mergedSize:y,handleScroll:_e,handleTabsResize:ge,cssVars:r?void 0:be,themeClass:xe?.themeClass,animationDirection:H,renderNameListRef:V,yScrollElRef:m,handleSegmentResize:ye,onAnimationBeforeLeave:re,onAnimationEnter:ie,onAnimationAfterEnter:se,onRender:xe?.onRender},ve)},render(){let{mergedClsPrefix:e,type:t,placement:n,addTabFixed:r,addable:i,mergedSize:a,renderNameListRef:o,onRender:s,paneWrapperClass:c,paneWrapperStyle:l,$slots:{default:u,prefix:d,suffix:f}}=this;s?.();let p=u?B(u()).filter(e=>e.type.__TAB_PANE__===!0):[],m=u?B(u()).filter(e=>e.type.__TAB__===!0):[],h=!m.length,g=t===`card`,_=t===`segment`,v=!g&&!_&&this.justifyContent;o.value=[];let b=()=>{let t=N(`div`,{style:this.tabWrapperStyle,class:`${e}-tabs-wrapper`},v?null:N(`div`,{class:`${e}-tabs-scroll-padding`,style:n===`top`||n===`bottom`?{width:`${this.tabsPadding}px`}:{height:`${this.tabsPadding}px`}}),h?p.map((e,t)=>(o.value.push(e.props.name),ve(N(Z,Object.assign({},e.props,{internalCreatedByPane:!0,internalLeftPadded:t!==0&&(!v||v===`center`||v===`start`||v===`end`)}),e.children?{default:e.children.tab}:void 0)))):m.map((e,t)=>(o.value.push(e.props.name),ve(t!==0&&!v?_e(e):e))),!r&&i&&g?$(i,(h?p.length:m.length)!==0):null,v?null:N(`div`,{class:`${e}-tabs-scroll-padding`,style:{width:`${this.tabsPadding}px`}}));return N(`div`,{ref:`tabsElRef`,class:`${e}-tabs-nav-scroll-content`},g&&i?N(L,{onResize:this.handleTabsResize},{default:()=>t}):t,g?N(`div`,{class:`${e}-tabs-pad`}):null,g?null:N(`div`,{ref:`barElRef`,class:`${e}-tabs-bar`}))},x=_?`top`:n;return N(`div`,{class:[`${e}-tabs`,this.themeClass,`${e}-tabs--${t}-type`,`${e}-tabs--${a}-size`,v&&`${e}-tabs--flex`,`${e}-tabs--${x}`],style:this.cssVars},N(`div`,{class:[`${e}-tabs-nav--${t}-type`,`${e}-tabs-nav--${x}`,`${e}-tabs-nav`]},y(d,t=>t&&N(`div`,{class:`${e}-tabs-nav__prefix`},t)),_?N(L,{onResize:this.handleSegmentResize},{default:()=>N(`div`,{class:`${e}-tabs-rail`,ref:`tabsElRef`},N(`div`,{class:`${e}-tabs-capsule`,ref:`segmentCapsuleElRef`},N(`div`,{class:`${e}-tabs-wrapper`},N(`div`,{class:`${e}-tabs-tab`}))),h?p.map((e,t)=>(o.value.push(e.props.name),N(Z,Object.assign({},e.props,{internalCreatedByPane:!0,internalLeftPadded:t!==0}),e.children?{default:e.children.tab}:void 0))):m.map((e,t)=>(o.value.push(e.props.name),t===0?e:_e(e))))}):N(L,{onResize:this.handleNavResize},{default:()=>N(`div`,{class:`${e}-tabs-nav-scroll-wrapper`,ref:`scrollWrapperElRef`},[`top`,`bottom`].includes(x)?N(ue,{ref:`xScrollInstRef`,onScroll:this.handleScroll},{default:b}):N(`div`,{class:`${e}-tabs-nav-y-scroll`,onScroll:this.handleScroll,ref:`yScrollElRef`},b()))}),r&&i&&g?$(i,!0):null,y(f,t=>t&&N(`div`,{class:`${e}-tabs-nav__suffix`},t))),h&&(this.animated&&(x===`top`||x===`bottom`)?N(`div`,{ref:`tabsPaneWrapperRef`,style:l,class:[`${e}-tabs-pane-wrapper`,c]},Q(p,this.mergedValue,this.renderedNames,this.onAnimationBeforeLeave,this.onAnimationEnter,this.onAnimationAfterEnter,this.animationDirection)):Q(p,this.mergedValue,this.renderedNames)))}});function Q(e,t,n,r,i,a,o){let s=[];return e.forEach(e=>{let{name:r,displayDirective:i,"display-directive":a}=e.props,o=e=>i===e||a===e,c=t===r;if(e.key!==void 0&&(e.key=r),c||o(`show`)||o(`show:lazy`)&&n.has(r)){n.has(r)||n.add(r);let t=!o(`if`);s.push(t?p(e,[[l,c]]):e)}}),o?N(c,{name:`${o}-transition`,onBeforeLeave:r,onEnter:i,onAfterEnter:a},{default:()=>s}):s}function $(e,t){return N(Z,{ref:`addTabInstRef`,key:`__addable`,name:`__addable`,internalCreatedByPane:!0,internalAddable:!0,internalLeftPadded:t,disabled:typeof e==`object`&&e.disabled})}function _e(t){let n=e(t);return n.props?n.props.internalLeftPadded=!0:n.props={internalLeftPadded:!0},n}function ve(e){return Array.isArray(e.dynamicProps)?e.dynamicProps.includes(`internalLeftPadded`)||e.dynamicProps.push(`internalLeftPadded`):e.dynamicProps=[`internalLeftPadded`],e}export{fe as n,ge as t};