import{d as C,e as h,a6 as v,E,ad as B,ai as S,W as r,aP as s,bp as A,a1 as f,bq as I,a3 as d,aR as W,br as M,bs as F,bt as L,$ as X,aQ as z,aU as _,a5 as P,g as q,bl as D,aW as V,b1 as U,bb as j,bu as Z,bv as G,b6 as H,be as K}from"./index-BeAjLgC_.js";import{g as Q}from"./TrashOutline-DLK0KsOh.js";const O=C({name:"SlotMachineNumber",props:{clsPrefix:{type:String,required:!0},value:{type:[Number,String],required:!0},oldOriginalNumber:{type:Number,default:void 0},newOriginalNumber:{type:Number,default:void 0}},setup(e){const o=h(null),i=h(e.value),l=h(e.value),t=h("up"),a=h(!1),m=v(()=>a.value?`${e.clsPrefix}-base-slot-machine-current-number--${t.value}-scroll`:null),g=v(()=>a.value?`${e.clsPrefix}-base-slot-machine-old-number--${t.value}-scroll`:null);E(S(e,"value"),(n,u)=>{i.value=u,l.value=n,B(N)});function N(){const n=e.newOriginalNumber,u=e.oldOriginalNumber;u===void 0||n===void 0||(n>u?w("up"):u>n&&w("down"))}function w(n){t.value=n,a.value=!1,B(()=>{var u;(u=o.value)===null||u===void 0||u.offsetWidth,a.value=!0})}return()=>{const{clsPrefix:n}=e;return r("span",{ref:o,class:`${n}-base-slot-machine-number`},i.value!==null?r("span",{class:[`${n}-base-slot-machine-old-number ${n}-base-slot-machine-old-number--top`,g.value]},i.value):null,r("span",{class:[`${n}-base-slot-machine-current-number`,m.value]},r("span",{ref:"numberWrapper",class:[`${n}-base-slot-machine-current-number__inner`,typeof e.value!="number"&&`${n}-base-slot-machine-current-number__inner--not-number`]},l.value)),i.value!==null?r("span",{class:[`${n}-base-slot-machine-old-number ${n}-base-slot-machine-old-number--bottom`,g.value]},i.value):null)}}}),{cubicBezierEaseOut:x}=A;function J({duration:e=".2s"}={}){return[s("&.fade-up-width-expand-transition-leave-active",{transition:`
 opacity ${e} ${x},
 max-width ${e} ${x},
 transform ${e} ${x}
 `}),s("&.fade-up-width-expand-transition-enter-active",{transition:`
 opacity ${e} ${x},
 max-width ${e} ${x},
 transform ${e} ${x}
 `}),s("&.fade-up-width-expand-transition-enter-to",{opacity:1,transform:"translateX(0) translateY(0)"}),s("&.fade-up-width-expand-transition-enter-from",{maxWidth:"0 !important",opacity:0,transform:"translateY(60%)"}),s("&.fade-up-width-expand-transition-leave-from",{opacity:1,transform:"translateY(0)"}),s("&.fade-up-width-expand-transition-leave-to",{maxWidth:"0 !important",opacity:0,transform:"translateY(60%)"})]}const ee=s([s("@keyframes n-base-slot-machine-fade-up-in",`
 from {
 transform: translateY(60%);
 opacity: 0;
 }
 to {
 transform: translateY(0);
 opacity: 1;
 }
 `),s("@keyframes n-base-slot-machine-fade-down-in",`
 from {
 transform: translateY(-60%);
 opacity: 0;
 }
 to {
 transform: translateY(0);
 opacity: 1;
 }
 `),s("@keyframes n-base-slot-machine-fade-up-out",`
 from {
 transform: translateY(0%);
 opacity: 1;
 }
 to {
 transform: translateY(-60%);
 opacity: 0;
 }
 `),s("@keyframes n-base-slot-machine-fade-down-out",`
 from {
 transform: translateY(0%);
 opacity: 1;
 }
 to {
 transform: translateY(60%);
 opacity: 0;
 }
 `),f("base-slot-machine",`
 overflow: hidden;
 white-space: nowrap;
 display: inline-block;
 height: 18px;
 line-height: 18px;
 `,[f("base-slot-machine-number",`
 display: inline-block;
 position: relative;
 height: 18px;
 width: .6em;
 max-width: .6em;
 `,[J({duration:".2s"}),I({duration:".2s",delay:"0s"}),f("base-slot-machine-old-number",`
 display: inline-block;
 opacity: 0;
 position: absolute;
 left: 0;
 right: 0;
 `,[d("top",{transform:"translateY(-100%)"}),d("bottom",{transform:"translateY(100%)"}),d("down-scroll",{animation:"n-base-slot-machine-fade-down-out .2s cubic-bezier(0, 0, .2, 1)",animationIterationCount:1}),d("up-scroll",{animation:"n-base-slot-machine-fade-up-out .2s cubic-bezier(0, 0, .2, 1)",animationIterationCount:1})]),f("base-slot-machine-current-number",`
 display: inline-block;
 position: absolute;
 left: 0;
 top: 0;
 bottom: 0;
 right: 0;
 opacity: 1;
 transform: translateY(0);
 width: .6em;
 `,[d("down-scroll",{animation:"n-base-slot-machine-fade-down-in .2s cubic-bezier(0, 0, .2, 1)",animationIterationCount:1}),d("up-scroll",{animation:"n-base-slot-machine-fade-up-in .2s cubic-bezier(0, 0, .2, 1)",animationIterationCount:1}),W("inner",`
 display: inline-block;
 position: absolute;
 right: 0;
 top: 0;
 width: .6em;
 `,[d("not-number",`
 right: unset;
 left: 0;
 `)])])])])]),ae=C({name:"BaseSlotMachine",props:{clsPrefix:{type:String,required:!0},value:{type:[Number,String],default:0},max:{type:Number,default:void 0},appeared:{type:Boolean,required:!0}},setup(e){M("-base-slot-machine",ee,S(e,"clsPrefix"));const o=h(),i=h(),l=v(()=>{if(typeof e.value=="string")return[];if(e.value<1)return[0];const t=[];let a=e.value;for(e.max!==void 0&&(a=Math.min(e.max,a));a>=1;)t.push(a%10),a/=10,a=Math.floor(a);return t.reverse(),t});return E(S(e,"value"),(t,a)=>{typeof t=="string"?(i.value=void 0,o.value=void 0):typeof a=="string"?(i.value=t,o.value=void 0):(i.value=t,o.value=a)}),()=>{const{value:t,clsPrefix:a}=e;return typeof t=="number"?r("span",{class:`${a}-base-slot-machine`},r(L,{name:"fade-up-width-expand-transition",tag:"span"},{default:()=>l.value.map((m,g)=>r(O,{clsPrefix:a,key:l.value.length-g-1,oldOriginalNumber:o.value,newOriginalNumber:i.value,value:m}))}),r(F,{key:"+",width:!0},{default:()=>e.max!==void 0&&e.max<t?r(O,{clsPrefix:a,value:"+"}):null})):r("span",{class:`${a}-base-slot-machine`},t)}}}),te=e=>{const{errorColor:o,infoColor:i,successColor:l,warningColor:t,fontFamily:a}=e;return{color:o,colorInfo:i,colorSuccess:l,colorError:o,colorWarning:t,fontSize:"12px",fontFamily:a}},ne={name:"Badge",common:X,self:te},oe=ne,ie=s([s("@keyframes badge-wave-spread",{from:{boxShadow:"0 0 0.5px 0px var(--n-ripple-color)",opacity:.6},to:{boxShadow:"0 0 0.5px 4.5px var(--n-ripple-color)",opacity:0}}),f("badge",`
 display: inline-flex;
 position: relative;
 vertical-align: middle;
 font-family: var(--n-font-family);
 `,[d("as-is",[f("badge-sup",{position:"static",transform:"translateX(0)"},[z({transformOrigin:"left bottom",originalTransform:"translateX(0)"})])]),d("dot",[f("badge-sup",`
 height: 8px;
 width: 8px;
 padding: 0;
 min-width: 8px;
 left: 100%;
 bottom: calc(100% - 4px);
 `,[s("::before","border-radius: 4px;")])]),f("badge-sup",`
 background: var(--n-color);
 transition:
 background-color .3s var(--n-bezier),
 color .3s var(--n-bezier);
 color: #FFF;
 position: absolute;
 height: 18px;
 line-height: 18px;
 border-radius: 9px;
 padding: 0 6px;
 text-align: center;
 font-size: var(--n-font-size);
 transform: translateX(-50%);
 left: 100%;
 bottom: calc(100% - 9px);
 font-variant-numeric: tabular-nums;
 z-index: 1;
 display: flex;
 align-items: center;
 `,[z({transformOrigin:"left bottom",originalTransform:"translateX(-50%)"}),f("base-wave",{zIndex:1,animationDuration:"2s",animationIterationCount:"infinite",animationDelay:"1s",animationTimingFunction:"var(--n-ripple-bezier)",animationName:"badge-wave-spread"}),s("&::before",`
 opacity: 0;
 transform: scale(1);
 border-radius: 9px;
 content: "";
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `)])])]),re=Object.assign(Object.assign({},P.props),{value:[String,Number],max:Number,dot:Boolean,type:{type:String,default:"default"},show:{type:Boolean,default:!0},showZero:Boolean,processing:Boolean,color:String,offset:Array}),ue=C({name:"Badge",props:re,setup(e,{slots:o}){const{mergedClsPrefixRef:i,inlineThemeDisabled:l,mergedRtlRef:t}=_(e),a=P("Badge","-badge",ie,oe,e,i),m=h(!1),g=()=>{m.value=!0},N=()=>{m.value=!1},w=v(()=>e.show&&(e.dot||e.value!==void 0&&!(!e.showZero&&Number(e.value)<=0)||!G(o.value)));q(()=>{w.value&&(m.value=!0)});const n=D("Badge",t,i),u=v(()=>{const{type:b,color:c}=e,{common:{cubicBezierEaseInOut:p,cubicBezierEaseOut:R},self:{[H("color",b)]:$,fontFamily:k,fontSize:T}}=a.value;return{"--n-font-size":T,"--n-font-family":k,"--n-color":c||$,"--n-ripple-color":c||$,"--n-bezier":p,"--n-ripple-bezier":R}}),y=l?V("badge",v(()=>{let b="";const{type:c,color:p}=e;return c&&(b+=c[0]),p&&(b+=K(p)),b}),u,e):void 0,Y=v(()=>{const{offset:b}=e;if(!b)return;const[c,p]=b,R=typeof c=="number"?`${c}px`:c,$=typeof p=="number"?`${p}px`:p;return{transform:`translate(calc(${n!=null&&n.value?"50%":"-50%"} + ${R}), ${$})`}});return{rtlEnabled:n,mergedClsPrefix:i,appeared:m,showBadge:w,handleAfterEnter:g,handleAfterLeave:N,cssVars:l?void 0:u,themeClass:y==null?void 0:y.themeClass,onRender:y==null?void 0:y.onRender,offsetStyle:Y}},render(){var e;const{mergedClsPrefix:o,onRender:i,themeClass:l,$slots:t}=this;i==null||i();const a=(e=t.default)===null||e===void 0?void 0:e.call(t);return r("div",{class:[`${o}-badge`,this.rtlEnabled&&`${o}-badge--rtl`,l,{[`${o}-badge--dot`]:this.dot,[`${o}-badge--as-is`]:!a}],style:this.cssVars},a,r(U,{name:"fade-in-scale-up-transition",onAfterEnter:this.handleAfterEnter,onAfterLeave:this.handleAfterLeave},{default:()=>this.showBadge?r("sup",{class:`${o}-badge-sup`,title:Q(this.value),style:this.offsetStyle},j(t.value,()=>[this.dot?null:r(ae,{clsPrefix:o,appeared:this.appeared,max:this.max,value:this.value})]),this.processing?r(Z,{clsPrefix:o}):null):null}))}});export{ue as N};
