import{At as e,Dt as t,Et as n,Fn as r,H as i,Hn as a,I as o,J as s,Mn as c,Mt as l,P as u,V as d,Vt as f,Wt as p,X as m,Xt as h,Y as g,_,_n as v,a as y,at as b,bn as x,c as S,cn as C,d as ee,dn as w,en as T,fn as E,gn as D,hn as O,it as k,jn as A,jt as j,kt as M,l as te,ln as ne,o as N,q as P,r as F,rt as I,u as L,un as R,v as re,wn as ie,xn as ae,yn as oe,zn as z}from"./Wave-BaVmjZD-.js";import{F as B,N as V,k as H,x as se}from"./apis-4W12woHh.js";import{c as U,d as ce,l as W,o as le,s as ue}from"./Button-CIcZXw0N.js";function de(e){return e.composedPath()[0]||null}function G(e){return e.composedPath()[0]}var fe={mousemoveoutside:new WeakMap,clickoutside:new WeakMap};function pe(e,t,n){if(e===`mousemoveoutside`){let e=e=>{t.contains(G(e))||n(e)};return{mousemove:e,touchstart:e}}if(e===`clickoutside`){let e=!1,r=n=>{e=!t.contains(G(n))},i=r=>{e&&(t.contains(G(r))||n(r))};return{mousedown:r,mouseup:i,touchstart:r,touchend:i}}return console.error(`[evtd/create-trap-handler]: name \`${e}\` is invalid. This could be a bug of evtd.`),{}}function K(e,t,n){let r=fe[e],i=r.get(t);i===void 0&&r.set(t,i=new WeakMap);let a=i.get(n);return a===void 0&&i.set(n,a=pe(e,t,n)),a}function q(e,t,n,r){if(e===`mousemoveoutside`||e===`clickoutside`){let i=K(e,t,n);return Object.keys(i).forEach(e=>{J(e,document,i[e],r)}),!0}return!1}function me(e,t,n,r){if(e===`mousemoveoutside`||e===`clickoutside`){let i=K(e,t,n);return Object.keys(i).forEach(e=>{Y(e,document,i[e],r)}),!0}return!1}function he(){if(typeof window>`u`)return{on:()=>{},off:()=>{}};let e=new WeakMap,t=new WeakMap;function n(){e.set(this,!0)}function r(){e.set(this,!0),t.set(this,!0)}function i(e,t,n){let r=e[t];return e[t]=function(){return n.apply(e,arguments),r.apply(e,arguments)},e}function a(e,t){e[t]=Event.prototype[t]}let o=new WeakMap,s=Object.getOwnPropertyDescriptor(Event.prototype,`currentTarget`);function c(){return o.get(this)??null}function l(e,t){s!==void 0&&Object.defineProperty(e,"currentTarget",{configurable:!0,enumerable:!0,get:t??s.get})}let u={bubble:{},capture:{}},d={};function f(){let s=function(s){let{type:d,eventPhase:f,bubbles:p}=s,m=G(s);if(f===2)return;let h=f===1?`capture`:`bubble`,g=m,_=[];for(;g===null&&(g=window),_.push(g),g!==window;)g=g.parentNode||null;let v=u.capture[d],y=u.bubble[d];if(i(s,`stopPropagation`,n),i(s,`stopImmediatePropagation`,r),l(s,c),h===`capture`){if(v===void 0)return;for(let n=_.length-1;n>=0&&!e.has(s);--n){let e=_[n],r=v.get(e);if(r!==void 0){o.set(s,e);for(let e of r){if(t.has(s))break;e(s)}}if(n===0&&!p&&y!==void 0){let n=y.get(e);if(n!==void 0)for(let e of n){if(t.has(s))break;e(s)}}}}else if(h===`bubble`){if(y===void 0)return;for(let n=0;n<_.length&&!e.has(s);++n){let e=_[n],r=y.get(e);if(r!==void 0){o.set(s,e);for(let e of r){if(t.has(s))break;e(s)}}}}a(s,`stopPropagation`),a(s,`stopImmediatePropagation`),l(s)};return s.displayName=`evtdUnifiedHandler`,s}function p(){let e=function(e){let{type:t,eventPhase:n}=e;if(n!==2)return;let r=d[t];r!==void 0&&r.forEach(t=>t(e))};return e.displayName=`evtdUnifiedWindowEventHandler`,e}let m=f(),h=p();function g(e,t){let n=u[e];return n[t]===void 0&&(n[t]=new Map,window.addEventListener(t,m,e===`capture`)),n[t]}function _(e){return d[e]===void 0&&(d[e]=new Set,window.addEventListener(e,h)),d[e]}function v(e,t){let n=e.get(t);return n===void 0&&e.set(t,n=new Set),n}function y(e,t,n,r){let i=u[t][n];if(i!==void 0){let t=i.get(e);if(t!==void 0&&t.has(r))return!0}return!1}function b(e,t){let n=d[e];return!!(n!==void 0&&n.has(t))}function x(e,t,n,r){let i;if(i=typeof r==`object`&&r.once===!0?a=>{S(e,t,i,r),n(a)}:n,q(e,t,i,r))return;let a=v(g(r===!0||typeof r==`object`&&r.capture===!0?`capture`:`bubble`,e),t);if(a.has(i)||a.add(i),t===window){let t=_(e);t.has(i)||t.add(i)}}function S(e,t,n,r){if(me(e,t,n,r))return;let i=r===!0||typeof r==`object`&&r.capture===!0,a=i?`capture`:`bubble`,o=g(a,e),s=v(o,t);if(t===window&&!y(t,i?`bubble`:`capture`,e,n)&&b(e,n)){let t=d[e];t.delete(n),t.size===0&&(window.removeEventListener(e,h),d[e]=void 0)}s.has(n)&&s.delete(n),s.size===0&&o.delete(t),o.size===0&&(window.removeEventListener(e,m,a===`capture`),u[a][e]=void 0)}return{on:x,off:S}}var{on:J,off:Y}=he();w();function ge(e,t){return A(e,e=>{e!==void 0&&(t.value=e)}),T(()=>e.value===void 0?t.value:e.value)}var _e=(typeof window>`u`?!1:/iPad|iPhone|iPod/.test(navigator.platform)||navigator.platform===`MacIntel`&&navigator.maxTouchPoints>1)&&!window.MSStream;function ve(){return _e}w();function ye(e){let t={isDeactivated:!1},n=!1;return v(()=>{t.isDeactivated=!1,n?e():n=!0}),x(()=>{t.isDeactivated=!0,n||=!0}),t}function be(e){let{left:t,right:n,top:r,bottom:i}=B(e);return`${r} ${t} ${i} ${n}`}w();var xe=C({render(){var e;return(e=this.$slots).default?.call(e)}}),Se=/\.|\[(?:[^[\]]*|(["'])(?:(?!\1)[^\\]|\\.)*?\1)\]/,Ce=/^\w*$/;function we(e,t){if(u(e))return!1;var n=typeof e;return n==`number`||n==`symbol`||n==`boolean`||e==null||o(e)?!0:Ce.test(e)||!Se.test(e)||t!=null&&e in Object(t)}var Te=`Expected a function`;function Ee(e,t){if(typeof e!=`function`||t!=null&&typeof t!=`function`)throw TypeError(Te);var n=function(){var r=arguments,i=t?t.apply(this,r):r[0],a=n.cache;if(a.has(i))return a.get(i);var o=e.apply(this,r);return n.cache=a.set(i,o)||a,o};return n.cache=new(Ee.Cache||re),n}Ee.Cache=re;var X=500;function Z(e){var t=Ee(e,function(e){return n.size===X&&n.clear(),e}),n=t.cache;return t}var De=/[^.[\]]+|\[(?:(-?\d+(?:\.\d+)?)|(["'])((?:(?!\2)[^\\]|\\.)*?)\2)\]|(?=(?:\.|\[\])(?:\.|\[\]|$))/g,Oe=/\\(\\)?/g,ke=Z(function(e){var t=[];return e.charCodeAt(0)===46&&t.push(``),e.replace(De,function(e,n,r,i){t.push(r?i.replace(Oe,`$1`):n||e)}),t});function Ae(e,t){return u(e)?e:we(e,t)?[e]:ke(_(e))}var Q=1/0;function $(e){if(typeof e==`string`||o(e))return e;var t=e+``;return t==`0`&&1/e==-Q?`-0`:t}function je(e,t){t=Ae(t,e);for(var n=0,r=t.length;e!=null&&n<r;)e=e[$(t[n++])];return n&&n==r?e:void 0}function Me(e,t,n){var r=e==null?void 0:je(e,t);return r===void 0?n:r}w();var Ne=C({name:`ChevronDown`,render(){return R(`svg`,{viewBox:`0 0 16 16`,fill:`none`,xmlns:`http://www.w3.org/2000/svg`},R(`path`,{d:`M3.14645 5.64645C3.34171 5.45118 3.65829 5.45118 3.85355 5.64645L8 9.79289L12.1464 5.64645C12.3417 5.45118 12.6583 5.45118 12.8536 5.64645C13.0488 5.84171 13.0488 6.15829 12.8536 6.35355L8.35355 10.8536C8.15829 11.0488 7.84171 11.0488 7.64645 10.8536L3.14645 6.35355C2.95118 6.15829 2.95118 5.84171 3.14645 5.64645Z`,fill:`currentColor`}))}});w();var Pe=y(`clear`,()=>R(`svg`,{viewBox:`0 0 16 16`,version:`1.1`,xmlns:`http://www.w3.org/2000/svg`},R(`g`,{stroke:`none`,"stroke-width":`1`,fill:`none`,"fill-rule":`evenodd`},R(`g`,{fill:`currentColor`,"fill-rule":`nonzero`},R(`path`,{d:`M8,2 C11.3137085,2 14,4.6862915 14,8 C14,11.3137085 11.3137085,14 8,14 C4.6862915,14 2,11.3137085 2,8 C2,4.6862915 4.6862915,2 8,2 Z M6.5343055,5.83859116 C6.33943736,5.70359511 6.07001296,5.72288026 5.89644661,5.89644661 L5.89644661,5.89644661 L5.83859116,5.9656945 C5.70359511,6.16056264 5.72288026,6.42998704 5.89644661,6.60355339 L5.89644661,6.60355339 L7.293,8 L5.89644661,9.39644661 L5.83859116,9.4656945 C5.70359511,9.66056264 5.72288026,9.92998704 5.89644661,10.1035534 L5.89644661,10.1035534 L5.9656945,10.1614088 C6.16056264,10.2964049 6.42998704,10.2771197 6.60355339,10.1035534 L6.60355339,10.1035534 L8,8.707 L9.39644661,10.1035534 L9.4656945,10.1614088 C9.66056264,10.2964049 9.92998704,10.2771197 10.1035534,10.1035534 L10.1035534,10.1035534 L10.1614088,10.0343055 C10.2964049,9.83943736 10.2771197,9.57001296 10.1035534,9.39644661 L10.1035534,9.39644661 L8.707,8 L10.1035534,6.60355339 L10.1614088,6.5343055 C10.2964049,6.33943736 10.2771197,6.07001296 10.1035534,5.89644661 L10.1035534,5.89644661 L10.0343055,5.83859116 C9.83943736,5.70359511 9.57001296,5.72288026 9.39644661,5.89644661 L9.39644661,5.89644661 L8,7.293 L6.60355339,5.89644661 Z`})))));w();var Fe=C({name:`Eye`,render(){return R(`svg`,{xmlns:`http://www.w3.org/2000/svg`,viewBox:`0 0 512 512`},R(`path`,{d:`M255.66 112c-77.94 0-157.89 45.11-220.83 135.33a16 16 0 0 0-.27 17.77C82.92 340.8 161.8 400 255.66 400c92.84 0 173.34-59.38 221.79-135.25a16.14 16.14 0 0 0 0-17.47C428.89 172.28 347.8 112 255.66 112z`,fill:`none`,stroke:`currentColor`,"stroke-linecap":`round`,"stroke-linejoin":`round`,"stroke-width":`32`}),R(`circle`,{cx:`256`,cy:`256`,r:`80`,fill:`none`,stroke:`currentColor`,"stroke-miterlimit":`10`,"stroke-width":`32`}))}});w();var Ie=C({name:`EyeOff`,render(){return R(`svg`,{xmlns:`http://www.w3.org/2000/svg`,viewBox:`0 0 512 512`},R(`path`,{d:`M432 448a15.92 15.92 0 0 1-11.31-4.69l-352-352a16 16 0 0 1 22.62-22.62l352 352A16 16 0 0 1 432 448z`,fill:`currentColor`}),R(`path`,{d:`M255.66 384c-41.49 0-81.5-12.28-118.92-36.5c-34.07-22-64.74-53.51-88.7-91v-.08c19.94-28.57 41.78-52.73 65.24-72.21a2 2 0 0 0 .14-2.94L93.5 161.38a2 2 0 0 0-2.71-.12c-24.92 21-48.05 46.76-69.08 76.92a31.92 31.92 0 0 0-.64 35.54c26.41 41.33 60.4 76.14 98.28 100.65C162 402 207.9 416 255.66 416a239.13 239.13 0 0 0 75.8-12.58a2 2 0 0 0 .77-3.31l-21.58-21.58a4 4 0 0 0-3.83-1a204.8 204.8 0 0 1-51.16 6.47z`,fill:`currentColor`}),R(`path`,{d:`M490.84 238.6c-26.46-40.92-60.79-75.68-99.27-100.53C349 110.55 302 96 255.66 96a227.34 227.34 0 0 0-74.89 12.83a2 2 0 0 0-.75 3.31l21.55 21.55a4 4 0 0 0 3.88 1a192.82 192.82 0 0 1 50.21-6.69c40.69 0 80.58 12.43 118.55 37c34.71 22.4 65.74 53.88 89.76 91a.13.13 0 0 1 0 .16a310.72 310.72 0 0 1-64.12 72.73a2 2 0 0 0-.15 2.95l19.9 19.89a2 2 0 0 0 2.7.13a343.49 343.49 0 0 0 68.64-78.48a32.2 32.2 0 0 0-.1-34.78z`,fill:`currentColor`}),R(`path`,{d:`M256 160a95.88 95.88 0 0 0-21.37 2.4a2 2 0 0 0-1 3.38l112.59 112.56a2 2 0 0 0 3.38-1A96 96 0 0 0 256 160z`,fill:`currentColor`}),R(`path`,{d:`M165.78 233.66a2 2 0 0 0-3.38 1a96 96 0 0 0 115 115a2 2 0 0 0 1-3.38z`,fill:`currentColor`}))}}),Le=t(`base-clear`,`
 flex-shrink: 0;
 height: 1em;
 width: 1em;
 position: relative;
`,[n(`>`,[M(`clear`,`
 font-size: var(--n-clear-size);
 height: 1em;
 width: 1em;
 cursor: pointer;
 color: var(--n-clear-color);
 transition: color .3s var(--n-bezier);
 display: flex;
 `,[n(`&:hover`,`
 color: var(--n-clear-color-hover)!important;
 `),n(`&:active`,`
 color: var(--n-clear-color-pressed)!important;
 `)]),M(`placeholder`,`
 display: flex;
 `),M(`clear, placeholder`,`
 position: absolute;
 left: 50%;
 top: 50%;
 transform: translateX(-50%) translateY(-50%);
 `,[U({originalTransform:`translateX(-50%) translateY(-50%)`,left:`50%`,top:`50%`})])])]);w(),r();var Re=C({name:`BaseClear`,props:{clsPrefix:{type:String,required:!0},show:Boolean,onClear:Function},setup(e){return te(`-base-clear`,Le,a(e,`clsPrefix`)),{handleMouseDown(e){e.preventDefault()}}},render(){let{clsPrefix:e}=this;return R(`div`,{class:`${e}-base-clear`},R(W,null,{default:()=>{var t;return this.show?R(`div`,{key:`dismiss`,class:`${e}-base-clear__clear`,onClick:this.onClear,onMousedown:this.handleMouseDown,"data-clear":!0},P(this.$slots.icon,()=>[R(N,{clsPrefix:e},{default:()=>R(Pe,null)})])):R(`div`,{key:`icon`,class:`${e}-base-clear__placeholder`},(t=this.$slots).placeholder?.call(t))}}))}}),{cubicBezierEaseInOut:ze}=L;function Be({name:e=`fade-in`,enterDuration:t=`0.2s`,leaveDuration:r=`0.2s`,enterCubicBezier:i=ze,leaveCubicBezier:a=ze}={}){return[n(`&.${e}-transition-enter-active`,{transition:`all ${t} ${i}!important`}),n(`&.${e}-transition-leave-active`,{transition:`all ${r} ${a}!important`}),n(`&.${e}-transition-enter-from, &.${e}-transition-leave-to`,{opacity:0}),n(`&.${e}-transition-leave-from, &.${e}-transition-enter-to`,{opacity:1})]}var Ve={railInsetHorizontalBottom:`auto 2px 4px 2px`,railInsetHorizontalTop:`4px 2px auto 2px`,railInsetVerticalRight:`2px 4px 2px auto`,railInsetVerticalLeft:`2px auto 2px 4px`,railColor:`transparent`};function He(e){let{scrollbarColor:t,scrollbarColorHover:n,scrollbarHeight:r,scrollbarWidth:i,scrollbarBorderRadius:a}=e;return Object.assign(Object.assign({},Ve),{height:r,width:i,borderRadius:a,color:t,colorHover:n})}var Ue={name:`Scrollbar`,common:F,self:He},We=t(`scrollbar`,`
 overflow: hidden;
 position: relative;
 z-index: auto;
 height: 100%;
 width: 100%;
`,[n(`>`,[t(`scrollbar-container`,`
 width: 100%;
 overflow: scroll;
 height: 100%;
 min-height: inherit;
 max-height: inherit;
 scrollbar-width: none;
 `,[n(`&::-webkit-scrollbar, &::-webkit-scrollbar-track-piece, &::-webkit-scrollbar-thumb`,`
 width: 0;
 height: 0;
 display: none;
 `),n(`>`,[t(`scrollbar-content`,`
 box-sizing: border-box;
 min-width: 100%;
 `)])])]),n(`>, +`,[t(`scrollbar-rail`,`
 position: absolute;
 pointer-events: none;
 user-select: none;
 background: var(--n-scrollbar-rail-color);
 -webkit-user-select: none;
 `,[e(`horizontal`,`
 height: var(--n-scrollbar-height);
 `,[n(`>`,[M(`scrollbar`,`
 height: var(--n-scrollbar-height);
 border-radius: var(--n-scrollbar-border-radius);
 right: 0;
 `)])]),e(`horizontal--top`,`
 top: var(--n-scrollbar-rail-top-horizontal-top); 
 right: var(--n-scrollbar-rail-right-horizontal-top); 
 bottom: var(--n-scrollbar-rail-bottom-horizontal-top); 
 left: var(--n-scrollbar-rail-left-horizontal-top); 
 `),e(`horizontal--bottom`,`
 top: var(--n-scrollbar-rail-top-horizontal-bottom); 
 right: var(--n-scrollbar-rail-right-horizontal-bottom); 
 bottom: var(--n-scrollbar-rail-bottom-horizontal-bottom); 
 left: var(--n-scrollbar-rail-left-horizontal-bottom); 
 `),e(`vertical`,`
 width: var(--n-scrollbar-width);
 `,[n(`>`,[M(`scrollbar`,`
 width: var(--n-scrollbar-width);
 border-radius: var(--n-scrollbar-border-radius);
 bottom: 0;
 `)])]),e(`vertical--left`,`
 top: var(--n-scrollbar-rail-top-vertical-left); 
 right: var(--n-scrollbar-rail-right-vertical-left); 
 bottom: var(--n-scrollbar-rail-bottom-vertical-left); 
 left: var(--n-scrollbar-rail-left-vertical-left); 
 `),e(`vertical--right`,`
 top: var(--n-scrollbar-rail-top-vertical-right); 
 right: var(--n-scrollbar-rail-right-vertical-right); 
 bottom: var(--n-scrollbar-rail-bottom-vertical-right); 
 left: var(--n-scrollbar-rail-left-vertical-right); 
 `),e(`disabled`,[n(`>`,[M(`scrollbar`,`pointer-events: none;`)])]),n(`>`,[M(`scrollbar`,`
 z-index: 1;
 position: absolute;
 cursor: pointer;
 pointer-events: all;
 background-color: var(--n-scrollbar-color);
 transition: background-color .2s var(--n-scrollbar-bezier);
 `,[Be(),n(`&:hover`,`background-color: var(--n-scrollbar-color-hover);`)])])])])]);w(),r(),p();var Ge=Object.assign(Object.assign({},S.props),{duration:{type:Number,default:0},scrollable:{type:Boolean,default:!0},xScrollable:Boolean,trigger:{type:String,default:`hover`},useUnifiedContainer:Boolean,triggerDisplayManually:Boolean,container:Function,content:Function,containerClass:String,containerStyle:[String,Object],contentClass:[String,Array],contentStyle:[String,Object],horizontalRailStyle:[String,Object],verticalRailStyle:[String,Object],onScroll:Function,onWheel:Function,onResize:Function,internalOnUpdateScrollLeft:Function,internalHoistYRail:Boolean,yPlacement:{type:String,default:`right`},xPlacement:{type:String,default:`bottom`}}),Ke=C({name:`Scrollbar`,props:Ge,inheritAttrs:!1,setup(e){let{mergedClsPrefixRef:t,inlineThemeDisabled:n,mergedRtlRef:r}=i(e),a=ee(`Scrollbar`,r,t),o=z(null),s=z(null),l=z(null),u=z(null),f=z(null),p=z(null),m=z(null),h=z(null),g=z(null),_=z(null),v=z(null),y=z(0),b=z(0),x=z(!1),C=z(!1),w=!1,E=!1,D,O,k=0,A=0,j=0,M=0,te=ve(),ne=S(`Scrollbar`,`-scrollbar`,We,Ue,e,t),N=T(()=>{let{value:e}=h,{value:t}=p,{value:n}=_;return e===null||t===null||n===null?0:Math.min(e,n*e/t+V(ne.value.self.width)*1.5)}),P=T(()=>`${N.value}px`),F=T(()=>{let{value:e}=g,{value:t}=m,{value:n}=v;return e===null||t===null||n===null?0:n*e/t+V(ne.value.self.height)*1.5}),I=T(()=>`${F.value}px`),L=T(()=>{let{value:e}=h,{value:t}=y,{value:n}=p,{value:r}=_;if(e===null||n===null||r===null)return 0;{let i=n-e;return i?t/i*(r-N.value):0}}),R=T(()=>`${L.value}px`),re=T(()=>{let{value:e}=g,{value:t}=b,{value:n}=m,{value:r}=v;if(e===null||n===null||r===null)return 0;{let i=n-e;return i?t/i*(r-F.value):0}}),ie=T(()=>`${re.value}px`),H=T(()=>{let{value:e}=h,{value:t}=p;return e!==null&&t!==null&&t>e}),se=T(()=>{let{value:e}=g,{value:t}=m;return e!==null&&t!==null&&t>e}),U=T(()=>{let{trigger:t}=e;return t===`none`||x.value}),ce=T(()=>{let{trigger:t}=e;return t===`none`||C.value}),W=T(()=>{let{container:t}=e;return t?t():s.value}),le=T(()=>{let{content:t}=e;return t?t():l.value}),ue=(t,n)=>{if(!e.scrollable)return;if(typeof t==`number`){q(t,n??0,0,!1,`auto`);return}let{left:r,top:i,index:a,elSize:o,position:s,behavior:c,el:l,debounce:u=!0}=t;(r!==void 0||i!==void 0)&&q(r??0,i??0,0,!1,c),l===void 0?a!==void 0&&o!==void 0?q(0,a*o,o,u,c):s===`bottom`?q(0,2**53-1,0,!1,c):s===`top`&&q(0,0,0,!1,c):q(0,l.offsetTop,l.offsetHeight,u,c)},G=ye(()=>{e.container||ue({top:y.value,left:b.value})}),fe=()=>{G.isDeactivated||Z()},pe=t=>{if(G.isDeactivated)return;let{onResize:n}=e;n&&n(t),Z()},K=(t,n)=>{if(!e.scrollable)return;let{value:r}=W;r&&(typeof t==`object`?r.scrollBy(t):r.scrollBy(t,n||0))};function q(e,t,n,r,i){let{value:a}=W;if(a){if(r){let{scrollTop:r,offsetHeight:o}=a;if(t>r){t+n<=r+o||a.scrollTo({left:e,top:t+n-o,behavior:i});return}}a.scrollTo({left:e,top:t,behavior:i})}}function me(){Se(),Ce(),Z()}function he(){ge()}function ge(){_e(),xe()}function _e(){O!==void 0&&window.clearTimeout(O),O=window.setTimeout(()=>{C.value=!1},e.duration)}function xe(){D!==void 0&&window.clearTimeout(D),D=window.setTimeout(()=>{x.value=!1},e.duration)}function Se(){D!==void 0&&window.clearTimeout(D),x.value=!0}function Ce(){O!==void 0&&window.clearTimeout(O),C.value=!0}function we(t){let{onScroll:n}=e;n&&n(t),Te()}function Te(){let{value:e}=W;e&&(y.value=e.scrollTop,b.value=e.scrollLeft*(a?.value?-1:1))}function Ee(){let{value:e}=le;e&&(p.value=e.offsetHeight,m.value=e.offsetWidth);let{value:t}=W;t&&(h.value=t.offsetHeight,g.value=t.offsetWidth);let{value:n}=f,{value:r}=u;n&&(v.value=n.offsetWidth),r&&(_.value=r.offsetHeight)}function X(){let{value:e}=W;e&&(y.value=e.scrollTop,b.value=e.scrollLeft*(a?.value?-1:1),h.value=e.offsetHeight,g.value=e.offsetWidth,p.value=e.scrollHeight,m.value=e.scrollWidth);let{value:t}=f,{value:n}=u;t&&(v.value=t.offsetWidth),n&&(_.value=n.offsetHeight)}function Z(){e.scrollable&&(e.useUnifiedContainer?X():(Ee(),Te()))}function De(e){return!o.value?.contains(de(e))}function Oe(e){e.preventDefault(),e.stopPropagation(),E=!0,J(`mousemove`,window,ke,!0),J(`mouseup`,window,Ae,!0),A=b.value,j=a?.value?window.innerWidth-e.clientX:e.clientX}function ke(t){if(!E)return;D!==void 0&&window.clearTimeout(D),O!==void 0&&window.clearTimeout(O);let{value:n}=g,{value:r}=m,{value:i}=F;if(n===null||r===null)return;let o=(a?.value?window.innerWidth-t.clientX-j:t.clientX-j)*(r-n)/(n-i),s=r-n,c=A+o;c=Math.min(s,c),c=Math.max(c,0);let{value:l}=W;if(l){l.scrollLeft=c*(a?.value?-1:1);let{internalOnUpdateScrollLeft:t}=e;t&&t(c)}}function Ae(e){e.preventDefault(),e.stopPropagation(),Y(`mousemove`,window,ke,!0),Y(`mouseup`,window,Ae,!0),E=!1,Z(),De(e)&&ge()}function Q(e){e.preventDefault(),e.stopPropagation(),w=!0,J(`mousemove`,window,$,!0),J(`mouseup`,window,je,!0),k=y.value,M=e.clientY}function $(e){if(!w)return;D!==void 0&&window.clearTimeout(D),O!==void 0&&window.clearTimeout(O);let{value:t}=h,{value:n}=p,{value:r}=N;if(t===null||n===null)return;let i=(e.clientY-M)*(n-t)/(t-r),a=n-t,o=k+i;o=Math.min(a,o),o=Math.max(o,0);let{value:s}=W;s&&(s.scrollTop=o)}function je(e){e.preventDefault(),e.stopPropagation(),Y(`mousemove`,window,$,!0),Y(`mouseup`,window,je,!0),w=!1,Z(),De(e)&&ge()}c(()=>{let{value:e}=se,{value:n}=H,{value:r}=t,{value:i}=f,{value:a}=u;i&&(e?i.classList.remove(`${r}-scrollbar-rail--disabled`):i.classList.add(`${r}-scrollbar-rail--disabled`)),a&&(n?a.classList.remove(`${r}-scrollbar-rail--disabled`):a.classList.add(`${r}-scrollbar-rail--disabled`))}),ae(()=>{e.container||Z()}),oe(()=>{D!==void 0&&window.clearTimeout(D),O!==void 0&&window.clearTimeout(O),Y(`mousemove`,window,$,!0),Y(`mouseup`,window,je,!0)});let Me=T(()=>{let{common:{cubicBezierEaseInOut:e},self:{color:t,colorHover:n,height:r,width:i,borderRadius:o,railInsetHorizontalTop:s,railInsetHorizontalBottom:c,railInsetVerticalRight:l,railInsetVerticalLeft:u,railColor:d}}=ne.value,{top:f,right:p,bottom:m,left:h}=B(s),{top:g,right:_,bottom:v,left:y}=B(c),{top:b,right:x,bottom:S,left:C}=B(a?.value?be(l):l),{top:ee,right:w,bottom:T,left:E}=B(a?.value?be(u):u);return{"--n-scrollbar-bezier":e,"--n-scrollbar-color":t,"--n-scrollbar-color-hover":n,"--n-scrollbar-border-radius":o,"--n-scrollbar-width":i,"--n-scrollbar-height":r,"--n-scrollbar-rail-top-horizontal-top":f,"--n-scrollbar-rail-right-horizontal-top":p,"--n-scrollbar-rail-bottom-horizontal-top":m,"--n-scrollbar-rail-left-horizontal-top":h,"--n-scrollbar-rail-top-horizontal-bottom":g,"--n-scrollbar-rail-right-horizontal-bottom":_,"--n-scrollbar-rail-bottom-horizontal-bottom":v,"--n-scrollbar-rail-left-horizontal-bottom":y,"--n-scrollbar-rail-top-vertical-right":b,"--n-scrollbar-rail-right-vertical-right":x,"--n-scrollbar-rail-bottom-vertical-right":S,"--n-scrollbar-rail-left-vertical-right":C,"--n-scrollbar-rail-top-vertical-left":ee,"--n-scrollbar-rail-right-vertical-left":w,"--n-scrollbar-rail-bottom-vertical-left":T,"--n-scrollbar-rail-left-vertical-left":E,"--n-scrollbar-rail-color":d}}),Ne=n?d(`scrollbar`,void 0,Me,e):void 0;return Object.assign(Object.assign({},{scrollTo:ue,scrollBy:K,sync:Z,syncUnifiedContainer:X,handleMouseEnterWrapper:me,handleMouseLeaveWrapper:he}),{mergedClsPrefix:t,rtlEnabled:a,containerScrollTop:y,wrapperRef:o,containerRef:s,contentRef:l,yRailRef:u,xRailRef:f,needYBar:H,needXBar:se,yBarSizePx:P,xBarSizePx:I,yBarTopPx:R,xBarLeftPx:ie,isShowXBar:U,isShowYBar:ce,isIos:te,handleScroll:we,handleContentResize:fe,handleContainerResize:pe,handleYScrollMouseDown:Q,handleXScrollMouseDown:Oe,cssVars:n?void 0:Me,themeClass:Ne?.themeClass,onRender:Ne?.onRender})},render(){let{$slots:e,mergedClsPrefix:t,triggerDisplayManually:n,rtlEnabled:r,internalHoistYRail:i,yPlacement:a,xPlacement:o,xScrollable:s}=this;if(!this.scrollable)return e.default?.call(e);let c=this.trigger===`none`,l=(e,n)=>R(`div`,{ref:`yRailRef`,class:[`${t}-scrollbar-rail`,`${t}-scrollbar-rail--vertical`,`${t}-scrollbar-rail--vertical--${a}`,e],"data-scrollbar-rail":!0,style:[n||``,this.verticalRailStyle],"aria-hidden":!0},R(c?xe:f,c?null:{name:`fade-in-transition`},{default:()=>this.needYBar&&this.isShowYBar&&!this.isIos?R(`div`,{class:`${t}-scrollbar-rail__scrollbar`,style:{height:this.yBarSizePx,top:this.yBarTopPx},onMousedown:this.handleYScrollMouseDown}):null})),u=()=>{var a;return(a=this.onRender)==null||a.call(this),R(`div`,O(this.$attrs,{role:`none`,ref:`wrapperRef`,class:[`${t}-scrollbar`,this.themeClass,r&&`${t}-scrollbar--rtl`],style:this.cssVars,onMouseenter:n?void 0:this.handleMouseEnterWrapper,onMouseleave:n?void 0:this.handleMouseLeaveWrapper}),[this.container?e.default?.call(e):R(`div`,{role:`none`,ref:`containerRef`,class:[`${t}-scrollbar-container`,this.containerClass],style:this.containerStyle,onScroll:this.handleScroll,onWheel:this.onWheel},R(H,{onResize:this.handleContentResize},{default:()=>R(`div`,{ref:`contentRef`,role:`none`,style:[{width:this.xScrollable?`fit-content`:null},this.contentStyle],class:[`${t}-scrollbar-content`,this.contentClass]},e)})),i?null:l(void 0,void 0),s&&R(`div`,{ref:`xRailRef`,class:[`${t}-scrollbar-rail`,`${t}-scrollbar-rail--horizontal`,`${t}-scrollbar-rail--horizontal--${o}`],style:this.horizontalRailStyle,"data-scrollbar-rail":!0,"aria-hidden":!0},R(c?xe:f,c?null:{name:`fade-in-transition`},{default:()=>this.needXBar&&this.isShowXBar&&!this.isIos?R(`div`,{class:`${t}-scrollbar-rail__scrollbar`,style:{width:this.xBarSizePx,right:r?this.xBarLeftPx:void 0,left:r?void 0:this.xBarLeftPx},onMousedown:this.handleXScrollMouseDown}):null}))])},d=this.container?u():R(H,{onResize:this.handleContainerResize},{default:u});return i?R(h,null,d,l(this.themeClass,this.cssVars)):d}}),qe=Ke;w();var Je=C({name:`InternalSelectionSuffix`,props:{clsPrefix:{type:String,required:!0},showArrow:{type:Boolean,default:void 0},showClear:{type:Boolean,default:void 0},loading:{type:Boolean,default:!1},onClear:Function},setup(e,{slots:t}){return()=>{let{clsPrefix:n}=e;return R(ue,{clsPrefix:n,class:`${n}-base-suffix`,strokeWidth:24,scale:.85,show:e.loading},{default:()=>e.showArrow?R(Re,{clsPrefix:n,show:e.showClear,onClear:e.onClear},{placeholder:()=>R(N,{clsPrefix:n,class:`${n}-base-suffix__arrow`},{default:()=>P(t.default,()=>[R(Ne,null)])})}):null})}}}),Ye={paddingTiny:`0 8px`,paddingSmall:`0 10px`,paddingMedium:`0 12px`,paddingLarge:`0 14px`,clearSize:`16px`};function Xe(e){let{textColor2:t,textColor3:n,textColorDisabled:r,primaryColor:i,primaryColorHover:a,inputColor:o,inputColorDisabled:s,borderColor:c,warningColor:l,warningColorHover:u,errorColor:d,errorColorHover:f,borderRadius:p,lineHeight:m,fontSizeTiny:h,fontSizeSmall:g,fontSizeMedium:_,fontSizeLarge:v,heightTiny:y,heightSmall:x,heightMedium:S,heightLarge:C,actionColor:ee,clearColor:w,clearColorHover:T,clearColorPressed:E,placeholderColor:D,placeholderColorDisabled:O,iconColor:k,iconColorDisabled:A,iconColorHover:j,iconColorPressed:M,fontWeight:te}=e;return Object.assign(Object.assign({},Ye),{fontWeight:te,countTextColorDisabled:r,countTextColor:n,heightTiny:y,heightSmall:x,heightMedium:S,heightLarge:C,fontSizeTiny:h,fontSizeSmall:g,fontSizeMedium:_,fontSizeLarge:v,lineHeight:m,lineHeightTextarea:m,borderRadius:p,iconSize:`16px`,groupLabelColor:ee,groupLabelTextColor:t,textColor:t,textColorDisabled:r,textDecorationColor:t,caretColor:i,placeholderColor:D,placeholderColorDisabled:O,color:o,colorDisabled:s,colorFocus:o,groupLabelBorder:`1px solid ${c}`,border:`1px solid ${c}`,borderHover:`1px solid ${a}`,borderDisabled:`1px solid ${c}`,borderFocus:`1px solid ${a}`,boxShadowFocus:`0 0 0 2px ${b(i,{alpha:.2})}`,loadingColor:i,loadingColorWarning:l,borderWarning:`1px solid ${l}`,borderHoverWarning:`1px solid ${u}`,colorFocusWarning:o,borderFocusWarning:`1px solid ${u}`,boxShadowFocusWarning:`0 0 0 2px ${b(l,{alpha:.2})}`,caretColorWarning:l,loadingColorError:d,borderError:`1px solid ${d}`,borderHoverError:`1px solid ${f}`,colorFocusError:o,borderFocusError:`1px solid ${f}`,boxShadowFocusError:`0 0 0 2px ${b(d,{alpha:.2})}`,caretColorError:d,clearColor:w,clearColorHover:T,clearColorPressed:E,iconColor:k,iconColorDisabled:A,iconColorHover:j,iconColorPressed:M,suffixTextColor:t})}var Ze={name:`Input`,common:F,self:Xe},Qe=I(`n-input`),$e=t(`input`,`
 max-width: 100%;
 cursor: text;
 line-height: 1.5;
 z-index: auto;
 outline: none;
 box-sizing: border-box;
 position: relative;
 display: inline-flex;
 border-radius: var(--n-border-radius);
 background-color: var(--n-color);
 transition: background-color .3s var(--n-bezier);
 font-size: var(--n-font-size);
 font-weight: var(--n-font-weight);
 --n-padding-vertical: calc((var(--n-height) - 1.5 * var(--n-font-size)) / 2);
`,[M(`input, textarea`,`
 overflow: hidden;
 flex-grow: 1;
 position: relative;
 `),M(`input-el, textarea-el, input-mirror, textarea-mirror, separator, placeholder`,`
 box-sizing: border-box;
 font-size: inherit;
 line-height: 1.5;
 font-family: inherit;
 border: none;
 outline: none;
 background-color: #0000;
 text-align: inherit;
 transition:
 -webkit-text-fill-color .3s var(--n-bezier),
 caret-color .3s var(--n-bezier),
 color .3s var(--n-bezier),
 text-decoration-color .3s var(--n-bezier);
 `),M(`input-el, textarea-el`,`
 -webkit-appearance: none;
 scrollbar-width: none;
 width: 100%;
 min-width: 0;
 text-decoration-color: var(--n-text-decoration-color);
 color: var(--n-text-color);
 caret-color: var(--n-caret-color);
 background-color: transparent;
 `,[n(`&::-webkit-scrollbar, &::-webkit-scrollbar-track-piece, &::-webkit-scrollbar-thumb`,`
 width: 0;
 height: 0;
 display: none;
 `),n(`&::placeholder`,`
 color: #0000;
 -webkit-text-fill-color: transparent !important;
 `),n(`&:-webkit-autofill ~`,[M(`placeholder`,`display: none;`)])]),e(`round`,[j(`textarea`,`border-radius: calc(var(--n-height) / 2);`)]),M(`placeholder`,`
 pointer-events: none;
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 overflow: hidden;
 color: var(--n-placeholder-color);
 `,[n(`span`,`
 width: 100%;
 display: inline-block;
 `)]),e(`textarea`,[M(`placeholder`,`overflow: visible;`)]),j(`autosize`,`width: 100%;`),e(`autosize`,[M(`textarea-el, input-el`,`
 position: absolute;
 top: 0;
 left: 0;
 height: 100%;
 `)]),t(`input-wrapper`,`
 overflow: hidden;
 display: inline-flex;
 flex-grow: 1;
 position: relative;
 padding-left: var(--n-padding-left);
 padding-right: var(--n-padding-right);
 `),M(`input-mirror`,`
 padding: 0;
 height: var(--n-height);
 line-height: var(--n-height);
 overflow: hidden;
 visibility: hidden;
 position: static;
 white-space: pre;
 pointer-events: none;
 `),M(`input-el`,`
 padding: 0;
 height: var(--n-height);
 line-height: var(--n-height);
 `,[n(`&[type=password]::-ms-reveal`,`display: none;`),n(`+`,[M(`placeholder`,`
 display: flex;
 align-items: center; 
 `)])]),j(`textarea`,[M(`placeholder`,`white-space: nowrap;`)]),M(`eye`,`
 display: flex;
 align-items: center;
 justify-content: center;
 transition: color .3s var(--n-bezier);
 `),e(`textarea`,`width: 100%;`,[t(`input-word-count`,`
 position: absolute;
 right: var(--n-padding-right);
 bottom: var(--n-padding-vertical);
 `),e(`resizable`,[t(`input-wrapper`,`
 resize: vertical;
 min-height: var(--n-height);
 `)]),M(`textarea-el, textarea-mirror, placeholder`,`
 height: 100%;
 padding-left: 0;
 padding-right: 0;
 padding-top: var(--n-padding-vertical);
 padding-bottom: var(--n-padding-vertical);
 word-break: break-word;
 display: inline-block;
 vertical-align: bottom;
 box-sizing: border-box;
 line-height: var(--n-line-height-textarea);
 margin: 0;
 resize: none;
 white-space: pre-wrap;
 scroll-padding-block-end: var(--n-padding-vertical);
 `),M(`textarea-mirror`,`
 width: 100%;
 pointer-events: none;
 overflow: hidden;
 visibility: hidden;
 position: static;
 white-space: pre-wrap;
 overflow-wrap: break-word;
 `)]),e(`pair`,[M(`input-el, placeholder`,`text-align: center;`),M(`separator`,`
 display: flex;
 align-items: center;
 transition: color .3s var(--n-bezier);
 color: var(--n-text-color);
 white-space: nowrap;
 `,[t(`icon`,`
 color: var(--n-icon-color);
 `),t(`base-icon`,`
 color: var(--n-icon-color);
 `)])]),e(`disabled`,`
 cursor: not-allowed;
 background-color: var(--n-color-disabled);
 `,[M(`border`,`border: var(--n-border-disabled);`),M(`input-el, textarea-el`,`
 cursor: not-allowed;
 color: var(--n-text-color-disabled);
 text-decoration-color: var(--n-text-color-disabled);
 `),M(`placeholder`,`color: var(--n-placeholder-color-disabled);`),M(`separator`,`color: var(--n-text-color-disabled);`,[t(`icon`,`
 color: var(--n-icon-color-disabled);
 `),t(`base-icon`,`
 color: var(--n-icon-color-disabled);
 `)]),t(`input-word-count`,`
 color: var(--n-count-text-color-disabled);
 `),M(`suffix, prefix`,`color: var(--n-text-color-disabled);`,[t(`icon`,`
 color: var(--n-icon-color-disabled);
 `),t(`internal-icon`,`
 color: var(--n-icon-color-disabled);
 `)])]),j(`disabled`,[M(`eye`,`
 color: var(--n-icon-color);
 cursor: pointer;
 `,[n(`&:hover`,`
 color: var(--n-icon-color-hover);
 `),n(`&:active`,`
 color: var(--n-icon-color-pressed);
 `)]),n(`&:hover`,[M(`state-border`,`border: var(--n-border-hover);`)]),e(`focus`,`background-color: var(--n-color-focus);`,[M(`state-border`,`
 border: var(--n-border-focus);
 box-shadow: var(--n-box-shadow-focus);
 `)])]),M(`border, state-border`,`
 box-sizing: border-box;
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 pointer-events: none;
 border-radius: inherit;
 border: var(--n-border);
 transition:
 box-shadow .3s var(--n-bezier),
 border-color .3s var(--n-bezier);
 `),M(`state-border`,`
 border-color: #0000;
 z-index: 1;
 `),M(`prefix`,`margin-right: 4px;`),M(`suffix`,`
 margin-left: 4px;
 `),M(`suffix, prefix`,`
 transition: color .3s var(--n-bezier);
 flex-wrap: nowrap;
 flex-shrink: 0;
 line-height: var(--n-height);
 white-space: nowrap;
 display: inline-flex;
 align-items: center;
 justify-content: center;
 color: var(--n-suffix-text-color);
 `,[t(`base-loading`,`
 font-size: var(--n-icon-size);
 margin: 0 2px;
 color: var(--n-loading-color);
 `),t(`base-clear`,`
 font-size: var(--n-icon-size);
 `,[M(`placeholder`,[t(`base-icon`,`
 transition: color .3s var(--n-bezier);
 color: var(--n-icon-color);
 font-size: var(--n-icon-size);
 `)])]),n(`>`,[t(`icon`,`
 transition: color .3s var(--n-bezier);
 color: var(--n-icon-color);
 font-size: var(--n-icon-size);
 `)]),t(`base-icon`,`
 font-size: var(--n-icon-size);
 `)]),t(`input-word-count`,`
 pointer-events: none;
 line-height: 1.5;
 font-size: .85em;
 color: var(--n-count-text-color);
 transition: color .3s var(--n-bezier);
 margin-left: 4px;
 font-variant: tabular-nums;
 `),[`warning`,`error`].map(r=>e(`${r}-status`,[j(`disabled`,[t(`base-loading`,`
 color: var(--n-loading-color-${r})
 `),M(`input-el, textarea-el`,`
 caret-color: var(--n-caret-color-${r});
 `),M(`state-border`,`
 border: var(--n-border-${r});
 `),n(`&:hover`,[M(`state-border`,`
 border: var(--n-border-hover-${r});
 `)]),n(`&:focus`,`
 background-color: var(--n-color-focus-${r});
 `,[M(`state-border`,`
 box-shadow: var(--n-box-shadow-focus-${r});
 border: var(--n-border-focus-${r});
 `)]),e(`focus`,`
 background-color: var(--n-color-focus-${r});
 `,[M(`state-border`,`
 box-shadow: var(--n-box-shadow-focus-${r});
 border: var(--n-border-focus-${r});
 `)])])]))]),et=t(`input`,[e(`disabled`,[M(`input-el, textarea-el`,`
 -webkit-text-fill-color: var(--n-text-color-disabled);
 `)])]);r(),w();function tt(e){let t=0;for(let n of e)t++;return t}function nt(e){return e===``||e==null}function rt(e){let t=z(null);function n(){let{value:n}=e;if(!n?.focus){i();return}let{selectionStart:r,selectionEnd:a,value:o}=n;r==null||a==null?i():t.value={start:r,end:a,beforeText:o.slice(0,r),afterText:o.slice(a)}}function r(){var n;let{value:r}=t,{value:i}=e;if(!r||!i)return;let{value:a}=i,{start:o,beforeText:s,afterText:c}=r,l=a.length;if(a.endsWith(c))l=a.length-c.length;else if(a.startsWith(s))l=s.length;else{let e=s[o-1],t=a.indexOf(e,o-1);t!==-1&&(l=t+1)}(n=i.setSelectionRange)==null||n.call(i,l,l)}function i(){t.value=null}return A(e,i),{recordCursor:n,restoreCursor:r}}w();var it=C({name:`InputWordCount`,setup(e,{slots:t}){let{mergedValueRef:n,maxlengthRef:r,mergedClsPrefixRef:i,countGraphemesRef:a}=E(Qe),o=T(()=>{let{value:e}=n;return e===null||Array.isArray(e)?0:(a.value||tt)(e)});return()=>{let{value:e}=r,{value:a}=n;return R(`span`,{class:`${i.value}-input-word-count`},s(t.default,{value:a===null||Array.isArray(a)?``:a},()=>[e===void 0?o.value:`${o.value} / ${e}`]))}}});w(),r();var at=Object.assign(Object.assign({},S.props),{bordered:{type:Boolean,default:void 0},type:{type:String,default:`text`},placeholder:[Array,String],defaultValue:{type:[String,Array],default:null},value:[String,Array],disabled:{type:Boolean,default:void 0},size:String,rows:{type:[Number,String],default:3},round:Boolean,minlength:[String,Number],maxlength:[String,Number],clearable:Boolean,autosize:{type:[Boolean,Object],default:!1},pair:Boolean,separator:String,readonly:{type:[String,Boolean],default:!1},passivelyActivated:Boolean,showPasswordOn:String,stateful:{type:Boolean,default:!0},autofocus:Boolean,inputProps:Object,resizable:{type:Boolean,default:!0},showCount:Boolean,loading:{type:Boolean,default:void 0},allowInput:Function,renderCount:Function,onMousedown:Function,onKeydown:Function,onKeyup:[Function,Array],onInput:[Function,Array],onFocus:[Function,Array],onBlur:[Function,Array],onClick:[Function,Array],onChange:[Function,Array],onClear:[Function,Array],countGraphemes:Function,status:String,"onUpdate:value":[Function,Array],onUpdateValue:[Function,Array],textDecoration:[String,Array],attrSize:{type:Number,default:20},onInputBlur:[Function,Array],onInputFocus:[Function,Array],onDeactivate:[Function,Array],onActivate:[Function,Array],onWrapperFocus:[Function,Array],onWrapperBlur:[Function,Array],internalDeactivateOnEnter:Boolean,internalForceFocus:Boolean,internalLoadingBeforeSuffix:{type:Boolean,default:!0},showPasswordToggle:Boolean}),ot=C({name:`Input`,props:at,slots:Object,setup(e){let{mergedClsPrefixRef:t,mergedBorderedRef:n,inlineThemeDisabled:r,mergedRtlRef:o}=i(e),s=S(`Input`,`-input`,$e,Ze,e,t);le&&te(`-input-safari`,et,t);let u=z(null),f=z(null),p=z(null),h=z(null),g=z(null),_=z(null),v=z(null),y=rt(v),b=z(null),{localeRef:x}=se(`Input`),C=z(e.defaultValue),w=ge(a(e,`value`),C),E=ce(e),{mergedSizeRef:O,mergedDisabledRef:j,mergedStatusRef:M}=E,N=z(!1),P=z(!1),F=z(!1),I=z(!1),L=null,R=T(()=>{let{placeholder:t,pair:n}=e;return n?Array.isArray(t)?t:t===void 0?[``,``]:[t,t]:t===void 0?[x.value.placeholder]:[t]}),re=T(()=>{let{value:e}=F,{value:t}=w,{value:n}=R;return!e&&(nt(t)||Array.isArray(t)&&nt(t[0]))&&n[0]}),oe=T(()=>{let{value:e}=F,{value:t}=w,{value:n}=R;return!e&&n[1]&&(nt(t)||Array.isArray(t)&&nt(t[1]))}),V=k(()=>e.internalForceFocus||N.value),H=k(()=>{if(j.value||e.readonly||!e.clearable||!V.value&&!P.value)return!1;let{value:t}=w,{value:n}=V;return e.pair?!!(Array.isArray(t)&&(t[0]||t[1]))&&(P.value||n):!!t&&(P.value||n)}),U=T(()=>{let{showPasswordOn:t}=e;if(t)return t;if(e.showPasswordToggle)return`click`}),W=z(!1),ue=T(()=>{let{textDecoration:t}=e;return t?Array.isArray(t)?t.map(e=>({textDecoration:e})):[{textDecoration:t}]:[``,``]}),de=z(void 0),G=()=>{if(e.type===`textarea`){let{autosize:t}=e;if(t&&(de.value=b.value?.$el?.offsetWidth),!f.value||typeof t==`boolean`)return;let{paddingTop:n,paddingBottom:r,lineHeight:i}=window.getComputedStyle(f.value),a=Number(n.slice(0,-2)),o=Number(r.slice(0,-2)),s=Number(i.slice(0,-2)),{value:c}=p;if(!c)return;if(t.minRows){let e=Math.max(t.minRows,1),n=`${a+o+s*e}px`;c.style.minHeight=n}if(t.maxRows){let e=`${a+o+s*t.maxRows}px`;c.style.maxHeight=e}}},fe=T(()=>{let{maxlength:t}=e;return t===void 0?void 0:Number(t)});ae(()=>{let{value:e}=w;Array.isArray(e)||Ye(e)});let pe=ne().proxy;function K(t,n){let{onUpdateValue:r,"onUpdate:value":i,onInput:a}=e,{nTriggerFormInput:o}=E;r&&m(r,t,n),i&&m(i,t,n),a&&m(a,t,n),C.value=t,o()}function q(t,n){let{onChange:r}=e,{nTriggerFormChange:i}=E;r&&m(r,t,n),C.value=t,i()}function me(t){let{onBlur:n}=e,{nTriggerFormBlur:r}=E;n&&m(n,t),r()}function he(t){let{onFocus:n}=e,{nTriggerFormFocus:r}=E;n&&m(n,t),r()}function _e(t){let{onClear:n}=e;n&&m(n,t)}function ve(t){let{onInputBlur:n}=e;n&&m(n,t)}function ye(t){let{onInputFocus:n}=e;n&&m(n,t)}function be(){let{onDeactivate:t}=e;t&&m(t)}function xe(){let{onActivate:t}=e;t&&m(t)}function Se(t){let{onClick:n}=e;n&&m(n,t)}function Ce(t){let{onWrapperFocus:n}=e;n&&m(n,t)}function we(t){let{onWrapperBlur:n}=e;n&&m(n,t)}function Te(){F.value=!0}function Ee(e){F.value=!1,e.target===_.value?X(e,1):X(e,0)}function X(t,n=0,r=`input`){let i=t.target.value;if(Ye(i),t instanceof InputEvent&&!t.isComposing&&(F.value=!1),e.type===`textarea`){let{value:e}=b;e&&e.syncUnifiedContainer()}if(L=i,F.value)return;y.recordCursor();let a=Z(i);if(a){if(!e.pair)r===`input`?K(i,{source:n}):q(i,{source:n});else{let{value:e}=w;e=Array.isArray(e)?[e[0],e[1]]:[``,``],e[n]=i,r===`input`?K(e,{source:n}):q(e,{source:n})}}pe.$forceUpdate(),a||D(y.restoreCursor)}function Z(t){let{countGraphemes:n,maxlength:r,minlength:i}=e;if(n){let e;if(r!==void 0&&(e===void 0&&(e=n(t)),e>Number(r))||i!==void 0&&(e===void 0&&(e=n(t)),e<Number(r)))return!1}let{allowInput:a}=e;return typeof a!=`function`||a(t)}function De(e){ve(e),e.relatedTarget===u.value&&be(),(e.relatedTarget===null||e.relatedTarget!==g.value&&e.relatedTarget!==_.value&&e.relatedTarget!==f.value)&&(I.value=!1),Q(e,`blur`),v.value=null}function Oe(e,t){ye(e),N.value=!0,I.value=!0,xe(),Q(e,`focus`),t===0?v.value=g.value:t===1?v.value=_.value:t===2&&(v.value=f.value)}function ke(t){e.passivelyActivated&&(we(t),Q(t,`blur`))}function Ae(t){e.passivelyActivated&&(N.value=!0,Ce(t),Q(t,`focus`))}function Q(e,t){e.relatedTarget!==null&&(e.relatedTarget===g.value||e.relatedTarget===_.value||e.relatedTarget===f.value||e.relatedTarget===u.value)||(t===`focus`?(he(e),N.value=!0):t===`blur`&&(me(e),N.value=!1))}function $(e,t){X(e,t,`change`)}function je(e){Se(e)}function Me(e){_e(e),Ne()}function Ne(){e.pair?(K([``,``],{source:`clear`}),q([``,``],{source:`clear`})):(K(``,{source:`clear`}),q(``,{source:`clear`}))}function Pe(t){let{onMousedown:n}=e;n&&n(t);let{tagName:r}=t.target;if(r!==`INPUT`&&r!==`TEXTAREA`){if(e.resizable){let{value:e}=u;if(e){let{left:n,top:r,width:i,height:a}=e.getBoundingClientRect();if(n+i-14<t.clientX&&t.clientX<n+i&&r+a-14<t.clientY&&t.clientY<r+a)return}}t.preventDefault(),N.value||Ue()}}function Fe(){var t;P.value=!0,e.type===`textarea`&&((t=b.value)==null||t.handleMouseEnterWrapper())}function Ie(){var t;P.value=!1,e.type===`textarea`&&((t=b.value)==null||t.handleMouseLeaveWrapper())}function Le(){j.value||U.value===`click`&&(W.value=!W.value)}function Re(e){if(j.value)return;e.preventDefault();let t=e=>{e.preventDefault(),Y(`mouseup`,document,t)};if(J(`mouseup`,document,t),U.value!==`mousedown`)return;W.value=!0;let n=()=>{W.value=!1,Y(`mouseup`,document,n)};J(`mouseup`,document,n)}function ze(t){e.onKeyup&&m(e.onKeyup,t)}function Be(t){switch(e.onKeydown&&m(e.onKeydown,t),t.key){case`Escape`:He();break;case`Enter`:Ve(t)}}function Ve(t){var n,r;if(e.passivelyActivated){let{value:i}=I;if(i){e.internalDeactivateOnEnter&&He();return}t.preventDefault(),e.type===`textarea`?(n=f.value)==null||n.focus():(r=g.value)==null||r.focus()}}function He(){e.passivelyActivated&&(I.value=!1,D(()=>{var e;(e=u.value)==null||e.focus()}))}function Ue(){var t,n,r;j.value||(e.passivelyActivated?(t=u.value)==null||t.focus():((n=f.value)==null||n.focus(),(r=g.value)==null||r.focus()))}function We(){u.value?.contains(document.activeElement)&&document.activeElement.blur()}function Ge(){var e,t;(e=f.value)==null||e.select(),(t=g.value)==null||t.select()}function Ke(){j.value||(f.value?f.value.focus():g.value&&g.value.focus())}function qe(){let{value:e}=u;e?.contains(document.activeElement)&&e!==document.activeElement&&He()}function Je(t){if(e.type===`textarea`){let{value:e}=f;e?.scrollTo(t)}else{let{value:e}=g;e?.scrollTo(t)}}function Ye(t){let{type:n,pair:r,autosize:i}=e;if(!r&&i){if(n===`textarea`){let{value:e}=p;e&&(e.textContent=`${t??``}\r\n`)}else{let{value:e}=h;e&&(t?e.textContent=t:e.innerHTML=`&nbsp;`)}}}function Xe(){G()}let tt=z({top:`0`});function it(e){var t;let{scrollTop:n}=e.target;tt.value.top=`${-n}px`,(t=b.value)==null||t.syncUnifiedContainer()}let at=null;c(()=>{let{autosize:t,type:n}=e;t&&n===`textarea`?at=A(w,e=>{!Array.isArray(e)&&e!==L&&Ye(e)}):at?.()});let ot=null;c(()=>{e.type===`textarea`?ot=A(w,e=>{var t;Array.isArray(e)||e===L||(t=b.value)==null||t.syncUnifiedContainer()}):ot?.()}),ie(Qe,{mergedValueRef:w,maxlengthRef:fe,mergedClsPrefixRef:t,countGraphemesRef:a(e,`countGraphemes`)});let st={wrapperElRef:u,inputElRef:g,textareaElRef:f,isCompositing:F,clear:Ne,focus:Ue,blur:We,select:Ge,deactivate:qe,activate:Ke,scrollTo:Je},ct=ee(`Input`,o,t),lt=T(()=>{let{value:e}=O,{common:{cubicBezierEaseInOut:t},self:{color:n,borderRadius:r,textColor:i,caretColor:a,caretColorError:o,caretColorWarning:c,textDecorationColor:u,border:d,borderDisabled:f,borderHover:p,borderFocus:m,placeholderColor:h,placeholderColorDisabled:g,lineHeightTextarea:_,colorDisabled:v,colorFocus:y,textColorDisabled:b,boxShadowFocus:x,iconSize:S,colorFocusWarning:C,boxShadowFocusWarning:ee,borderWarning:w,borderFocusWarning:T,borderHoverWarning:E,colorFocusError:D,boxShadowFocusError:k,borderError:A,borderFocusError:j,borderHoverError:M,clearSize:te,clearColor:ne,clearColorHover:N,clearColorPressed:P,iconColor:F,iconColorDisabled:I,suffixTextColor:L,countTextColor:R,countTextColorDisabled:re,iconColorHover:ie,iconColorPressed:ae,loadingColor:oe,loadingColorError:z,loadingColorWarning:V,fontWeight:H,[l(`padding`,e)]:se,[l(`fontSize`,e)]:U,[l(`height`,e)]:ce}}=s.value,{left:W,right:le}=B(se);return{"--n-bezier":t,"--n-count-text-color":R,"--n-count-text-color-disabled":re,"--n-color":n,"--n-font-size":U,"--n-font-weight":H,"--n-border-radius":r,"--n-height":ce,"--n-padding-left":W,"--n-padding-right":le,"--n-text-color":i,"--n-caret-color":a,"--n-text-decoration-color":u,"--n-border":d,"--n-border-disabled":f,"--n-border-hover":p,"--n-border-focus":m,"--n-placeholder-color":h,"--n-placeholder-color-disabled":g,"--n-icon-size":S,"--n-line-height-textarea":_,"--n-color-disabled":v,"--n-color-focus":y,"--n-text-color-disabled":b,"--n-box-shadow-focus":x,"--n-loading-color":oe,"--n-caret-color-warning":c,"--n-color-focus-warning":C,"--n-box-shadow-focus-warning":ee,"--n-border-warning":w,"--n-border-focus-warning":T,"--n-border-hover-warning":E,"--n-loading-color-warning":V,"--n-caret-color-error":o,"--n-color-focus-error":D,"--n-box-shadow-focus-error":k,"--n-border-error":A,"--n-border-focus-error":j,"--n-border-hover-error":M,"--n-loading-color-error":z,"--n-clear-color":ne,"--n-clear-size":te,"--n-clear-color-hover":N,"--n-clear-color-pressed":P,"--n-icon-color":F,"--n-icon-color-hover":ie,"--n-icon-color-pressed":ae,"--n-icon-color-disabled":I,"--n-suffix-text-color":L}}),ut=r?d(`input`,T(()=>{let{value:e}=O;return e[0]}),lt,e):void 0;return Object.assign(Object.assign({},st),{wrapperElRef:u,inputElRef:g,inputMirrorElRef:h,inputEl2Ref:_,textareaElRef:f,textareaMirrorElRef:p,textareaScrollbarInstRef:b,rtlEnabled:ct,uncontrolledValue:C,mergedValue:w,passwordVisible:W,mergedPlaceholder:R,showPlaceholder1:re,showPlaceholder2:oe,mergedFocus:V,isComposing:F,activated:I,showClearButton:H,mergedSize:O,mergedDisabled:j,textDecorationStyle:ue,mergedClsPrefix:t,mergedBordered:n,mergedShowPasswordOn:U,placeholderStyle:tt,mergedStatus:M,textAreaScrollContainerWidth:de,handleTextAreaScroll:it,handleCompositionStart:Te,handleCompositionEnd:Ee,handleInput:X,handleInputBlur:De,handleInputFocus:Oe,handleWrapperBlur:ke,handleWrapperFocus:Ae,handleMouseEnter:Fe,handleMouseLeave:Ie,handleMouseDown:Pe,handleChange:$,handleClick:je,handleClear:Me,handlePasswordToggleClick:Le,handlePasswordToggleMousedown:Re,handleWrapperKeydown:Be,handleWrapperKeyup:ze,handleTextAreaMirrorResize:Xe,getTextareaScrollContainer:()=>f.value,mergedTheme:s,cssVars:r?void 0:lt,themeClass:ut?.themeClass,onRender:ut?.onRender})},render(){let{mergedClsPrefix:e,mergedStatus:t,themeClass:n,type:r,countGraphemes:i,onRender:a}=this,o=this.$slots;return a?.(),R(`div`,{ref:`wrapperElRef`,class:[`${e}-input`,n,t&&`${e}-input--${t}-status`,{[`${e}-input--rtl`]:this.rtlEnabled,[`${e}-input--disabled`]:this.mergedDisabled,[`${e}-input--textarea`]:r===`textarea`,[`${e}-input--resizable`]:this.resizable&&!this.autosize,[`${e}-input--autosize`]:this.autosize,[`${e}-input--round`]:this.round&&r!==`textarea`,[`${e}-input--pair`]:this.pair,[`${e}-input--focus`]:this.mergedFocus,[`${e}-input--stateful`]:this.stateful}],style:this.cssVars,tabindex:!this.mergedDisabled&&this.passivelyActivated&&!this.activated?0:void 0,onFocus:this.handleWrapperFocus,onBlur:this.handleWrapperBlur,onClick:this.handleClick,onMousedown:this.handleMouseDown,onMouseenter:this.handleMouseEnter,onMouseleave:this.handleMouseLeave,onCompositionstart:this.handleCompositionStart,onCompositionend:this.handleCompositionEnd,onKeyup:this.handleWrapperKeyup,onKeydown:this.handleWrapperKeydown},R(`div`,{class:`${e}-input-wrapper`},g(o.prefix,t=>t&&R(`div`,{class:`${e}-input__prefix`},t)),r===`textarea`?R(Ke,{ref:`textareaScrollbarInstRef`,class:`${e}-input__textarea`,container:this.getTextareaScrollContainer,triggerDisplayManually:!0,useUnifiedContainer:!0,internalHoistYRail:!0},{default:()=>{let{textAreaScrollContainerWidth:t}=this,n={width:this.autosize&&t&&`${t}px`};return R(h,null,R(`textarea`,Object.assign({},this.inputProps,{ref:`textareaElRef`,class:[`${e}-input__textarea-el`,this.inputProps?.class],autofocus:this.autofocus,rows:Number(this.rows),placeholder:this.placeholder,value:this.mergedValue,disabled:this.mergedDisabled,maxlength:i?void 0:this.maxlength,minlength:i?void 0:this.minlength,readonly:this.readonly,tabindex:this.passivelyActivated&&!this.activated?-1:void 0,style:[this.textDecorationStyle[0],this.inputProps?.style,n],onBlur:this.handleInputBlur,onFocus:e=>{this.handleInputFocus(e,2)},onInput:this.handleInput,onChange:this.handleChange,onScroll:this.handleTextAreaScroll})),this.showPlaceholder1?R(`div`,{class:`${e}-input__placeholder`,style:[this.placeholderStyle,n],key:`placeholder`},this.mergedPlaceholder[0]):null,this.autosize?R(H,{onResize:this.handleTextAreaMirrorResize},{default:()=>R(`div`,{ref:`textareaMirrorElRef`,class:`${e}-input__textarea-mirror`,key:`mirror`})}):null)}}):R(`div`,{class:`${e}-input__input`},R(`input`,Object.assign({type:r===`password`&&this.mergedShowPasswordOn&&this.passwordVisible?`text`:r},this.inputProps,{ref:`inputElRef`,class:[`${e}-input__input-el`,this.inputProps?.class],style:[this.textDecorationStyle[0],this.inputProps?.style],tabindex:this.passivelyActivated&&!this.activated?-1:void 0,placeholder:this.mergedPlaceholder[0],disabled:this.mergedDisabled,maxlength:i?void 0:this.maxlength,minlength:i?void 0:this.minlength,value:Array.isArray(this.mergedValue)?this.mergedValue[0]:this.mergedValue,readonly:this.readonly,autofocus:this.autofocus,size:this.attrSize,onBlur:this.handleInputBlur,onFocus:e=>{this.handleInputFocus(e,0)},onInput:e=>{this.handleInput(e,0)},onChange:e=>{this.handleChange(e,0)}})),this.showPlaceholder1?R(`div`,{class:`${e}-input__placeholder`},R(`span`,null,this.mergedPlaceholder[0])):null,this.autosize?R(`div`,{class:`${e}-input__input-mirror`,key:`mirror`,ref:`inputMirrorElRef`},`\xA0`):null),!this.pair&&g(o.suffix,t=>t||this.clearable||this.showCount||this.mergedShowPasswordOn||this.loading!==void 0?R(`div`,{class:`${e}-input__suffix`},[g(o[`clear-icon-placeholder`],t=>(this.clearable||t)&&R(Re,{clsPrefix:e,show:this.showClearButton,onClear:this.handleClear},{placeholder:()=>t,icon:()=>{var e;return(e=this.$slots)[`clear-icon`]?.call(e)}})),this.internalLoadingBeforeSuffix?null:t,this.loading===void 0?null:R(Je,{clsPrefix:e,loading:this.loading,showArrow:!1,showClear:!1,style:this.cssVars}),this.internalLoadingBeforeSuffix?t:null,this.showCount&&this.type!==`textarea`?R(it,null,{default:e=>{let{renderCount:t}=this;return t?t(e):o.count?.call(o,e)}}):null,this.mergedShowPasswordOn&&this.type===`password`?R(`div`,{class:`${e}-input__eye`,onMousedown:this.handlePasswordToggleMousedown,onClick:this.handlePasswordToggleClick},this.passwordVisible?P(o[`password-visible-icon`],()=>[R(N,{clsPrefix:e},{default:()=>R(Fe,null)})]):P(o[`password-invisible-icon`],()=>[R(N,{clsPrefix:e},{default:()=>R(Ie,null)})])):null]):null)),this.pair?R(`span`,{class:`${e}-input__separator`},P(o.separator,()=>[this.separator])):null,this.pair?R(`div`,{class:`${e}-input-wrapper`},R(`div`,{class:`${e}-input__input`},R(`input`,{ref:`inputEl2Ref`,type:this.type,class:`${e}-input__input-el`,tabindex:this.passivelyActivated&&!this.activated?-1:void 0,placeholder:this.mergedPlaceholder[1],disabled:this.mergedDisabled,maxlength:i?void 0:this.maxlength,minlength:i?void 0:this.minlength,value:Array.isArray(this.mergedValue)?this.mergedValue[1]:void 0,readonly:this.readonly,style:this.textDecorationStyle[1],onBlur:this.handleInputBlur,onFocus:e=>{this.handleInputFocus(e,1)},onInput:e=>{this.handleInput(e,1)},onChange:e=>{this.handleChange(e,1)}}),this.showPlaceholder2?R(`div`,{class:`${e}-input__placeholder`},R(`span`,null,this.mergedPlaceholder[1])):null),g(o.suffix,t=>(this.clearable||t)&&R(`div`,{class:`${e}-input__suffix`},[this.clearable&&R(Re,{clsPrefix:e,show:this.showClearButton,onClear:this.handleClear},{icon:()=>o[`clear-icon`]?.call(o),placeholder:()=>o[`clear-icon-placeholder`]?.call(o)}),t]))):null,this.mergedBordered?R(`div`,{class:`${e}-input__border`}):null,this.mergedBordered?R(`div`,{class:`${e}-input__state-border`}):null,this.showCount&&r===`textarea`?R(it,null,{default:e=>{let{renderCount:t}=this;return t?t(e):o.count?.call(o,e)}}):null)}});export{de as S,xe as _,Ke as a,Y as b,He as c,Ne as d,Me as f,we as g,Ae as h,Je as i,Be as l,$ as m,Ze as n,qe as o,je as p,Ye as r,Ue as s,ot as t,Fe as u,ye as v,J as x,ge as y};