import{$ as e,A as t,C as n,E as r,I as i,Q as a,R as o,T as s,U as c,X as l,Y as u,et as d,it as f,w as p,z as m}from"./Modal-SNnNCmng.js";import{$ as h,At as g,Dt as _,Et as v,Fn as y,H as b,Hn as x,Ln as S,Mt as C,Rn as w,V as T,Vt as E,Wt as D,X as O,Xt as k,at as A,c as j,cn as M,dn as N,en as P,fn as F,hn as I,it as L,jn as R,jt as ee,kt as z,r as te,rt as B,s as ne,un as V,vn as re,wn as H,yn as ie,zn as U}from"./Wave-BaVmjZD-.js";import{b as W,o as ae,x as G,y as oe}from"./Input-uWJcXTtk.js";import{C as se,m as ce}from"./GlobalStore-sP_W_bFV.js";import{y as le}from"./apis-4W12woHh.js";N(),y();function K(e={},t){let n=S({ctrl:!1,command:!1,win:!1,shift:!1,tab:!1}),{keydown:r,keyup:i}=e,a=e=>{switch(e.key){case`Control`:n.ctrl=!0;break;case`Meta`:n.command=!0,n.win=!0;break;case`Shift`:n.shift=!0;break;case`Tab`:n.tab=!0}r!==void 0&&Object.keys(r).forEach(t=>{if(t!==e.key)return;let n=r[t];if(typeof n==`function`)n(e);else{let{stop:t=!1,prevent:r=!1}=n;t&&e.stopPropagation(),r&&e.preventDefault(),n.handler(e)}})},o=e=>{switch(e.key){case`Control`:n.ctrl=!1;break;case`Meta`:n.command=!1,n.win=!1;break;case`Shift`:n.shift=!1;break;case`Tab`:n.tab=!1}i!==void 0&&Object.keys(i).forEach(t=>{if(t!==e.key)return;let n=i[t];if(typeof n==`function`)n(e);else{let{stop:t=!1,prevent:r=!1}=n;t&&e.stopPropagation(),r&&e.preventDefault(),n.handler(e)}})},s=()=>{(t===void 0||t.value)&&(G(`keydown`,document,a),G(`keyup`,document,o)),t!==void 0&&R(t,e=>{e?(G(`keydown`,document,a),G(`keyup`,document,o)):(W(`keydown`,document,a),W(`keyup`,document,o))})};return se()?(re(s),ie(()=>{(t===void 0||t.value)&&(W(`keydown`,document,a),W(`keyup`,document,o))})):s(),w(n)}y(),N();function ue(e,t,n){if(!t)return e;let r=U(e.value),i=null;return R(e,e=>{i!==null&&window.clearTimeout(i),e===!0?n&&!n.value?r.value=!0:i=window.setTimeout(()=>{r.value=!0},t):r.value=!1}),r}N();var q=M({name:`ChevronRight`,render(){return V(`svg`,{viewBox:`0 0 16 16`,fill:`none`,xmlns:`http://www.w3.org/2000/svg`},V(`path`,{d:`M5.64645 3.14645C5.45118 3.34171 5.45118 3.65829 5.64645 3.85355L9.79289 8L5.64645 12.1464C5.45118 12.3417 5.45118 12.6583 5.64645 12.8536C5.84171 13.0488 6.15829 13.0488 6.35355 12.8536L10.8536 8.35355C11.0488 8.15829 11.0488 7.84171 10.8536 7.64645L6.35355 3.14645C6.15829 2.95118 5.84171 2.95118 5.64645 3.14645Z`,fill:`currentColor`}))}}),de={padding:`4px 0`,optionIconSizeSmall:`14px`,optionIconSizeMedium:`16px`,optionIconSizeLarge:`16px`,optionIconSizeHuge:`18px`,optionSuffixWidthSmall:`14px`,optionSuffixWidthMedium:`14px`,optionSuffixWidthLarge:`16px`,optionSuffixWidthHuge:`16px`,optionIconSuffixWidthSmall:`32px`,optionIconSuffixWidthMedium:`32px`,optionIconSuffixWidthLarge:`36px`,optionIconSuffixWidthHuge:`36px`,optionPrefixWidthSmall:`14px`,optionPrefixWidthMedium:`14px`,optionPrefixWidthLarge:`16px`,optionPrefixWidthHuge:`16px`,optionIconPrefixWidthSmall:`36px`,optionIconPrefixWidthMedium:`36px`,optionIconPrefixWidthLarge:`40px`,optionIconPrefixWidthHuge:`40px`};function J(e){let{primaryColor:t,textColor2:n,dividerColor:r,hoverColor:i,popoverColor:a,invertedColor:o,borderRadius:s,fontSizeSmall:c,fontSizeMedium:l,fontSizeLarge:u,fontSizeHuge:d,heightSmall:f,heightMedium:p,heightLarge:m,heightHuge:h,textColor3:g,opacityDisabled:_}=e;return Object.assign(Object.assign({},de),{optionHeightSmall:f,optionHeightMedium:p,optionHeightLarge:m,optionHeightHuge:h,borderRadius:s,fontSizeSmall:c,fontSizeMedium:l,fontSizeLarge:u,fontSizeHuge:d,optionTextColor:n,optionTextColorHover:n,optionTextColorActive:t,optionTextColorChildActive:t,color:a,dividerColor:r,suffixColor:n,prefixColor:n,optionColorHover:i,optionColorActive:A(t,{alpha:.1}),groupHeaderTextColor:g,optionTextColorInverted:`#BBB`,optionTextColorHoverInverted:`#FFF`,optionTextColorActiveInverted:`#FFF`,optionTextColorChildActiveInverted:`#FFF`,colorInverted:o,dividerColorInverted:`#BBB`,suffixColorInverted:`#BBB`,prefixColorInverted:`#BBB`,optionColorHoverInverted:t,optionColorActiveInverted:t,groupHeaderTextColorInverted:`#AAA`,optionOpacityDisabled:_})}var Y=ne({name:`Dropdown`,common:te,peers:{Popover:r},self:J}),X=B(`n-dropdown-menu`),Z=B(`n-dropdown`),fe=B(`n-dropdown-option`);N();var pe=M({name:`DropdownDivider`,props:{clsPrefix:{type:String,required:!0}},render(){return V(`div`,{class:`${this.clsPrefix}-dropdown-divider`})}});N();var me=M({name:`DropdownGroupHeader`,props:{clsPrefix:{type:String,required:!0},tmNode:{type:Object,required:!0}},setup(){let{showIconRef:e,hasSubmenuRef:t}=F(X),{renderLabelRef:n,labelFieldRef:r,nodePropsRef:i,renderOptionRef:a}=F(Z);return{labelField:r,showIcon:e,hasSubmenu:t,renderLabel:n,nodeProps:i,renderOption:a}},render(){let{clsPrefix:e,hasSubmenu:t,showIcon:n,nodeProps:r,renderLabel:a,renderOption:o}=this,{rawNode:s}=this.tmNode,c=V(`div`,Object.assign({class:`${e}-dropdown-option`},r?.(s)),V(`div`,{class:`${e}-dropdown-option-body ${e}-dropdown-option-body--group`},V(`div`,{"data-dropdown-option":!0,class:[`${e}-dropdown-option-body__prefix`,n&&`${e}-dropdown-option-body__prefix--show-icon`]},i(s.icon)),V(`div`,{class:`${e}-dropdown-option-body__label`,"data-dropdown-option":!0},a?a(s):i(s.title??s[this.labelField])),V(`div`,{class:[`${e}-dropdown-option-body__suffix`,t&&`${e}-dropdown-option-body__suffix--has-submenu`],"data-dropdown-option":!0})));return o?o({node:c,option:s}):c}});function Q(e,t){return e.type===`submenu`||e.type===void 0&&e[t]!==void 0}function he(e){return e.type===`group`}function ge(e){return e.type===`divider`}function _e(e){return e.type===`render`}N(),y(),D();var $=M({name:`DropdownOption`,props:{clsPrefix:{type:String,required:!0},tmNode:{type:Object,required:!0},parentKey:{type:[String,Number],default:null},placement:{type:String,default:`right-start`},props:Object,scrollable:Boolean},setup(e){let t=F(Z),{hoverKeyRef:n,keyboardKeyRef:r,lastToggledSubmenuKeyRef:i,pendingKeyPathRef:o,activeKeyPathRef:s,animatedRef:c,mergedShowRef:l,renderLabelRef:u,renderIconRef:d,labelFieldRef:p,childrenFieldRef:m,renderOptionRef:h,nodePropsRef:g,menuPropsRef:_}=t,v=F(fe,null),y=F(X),b=F(a),x=P(()=>e.tmNode.rawNode),S=P(()=>{let{value:t}=m;return Q(e.tmNode.rawNode,t)}),C=P(()=>{let{disabled:t}=e.tmNode;return t}),w=ue(P(()=>{if(!S.value)return!1;let{key:t,disabled:a}=e.tmNode;if(a)return!1;let{value:s}=n,{value:c}=r,{value:l}=i,{value:u}=o;return s===null?c===null?l!==null&&u.includes(t):u.includes(t)&&u[u.length-1]!==t:u.includes(t)}),300,P(()=>r.value===null&&!c.value)),T=P(()=>!!v?.enteringSubmenuRef.value),E=U(!1);H(fe,{enteringSubmenuRef:E});function D(){E.value=!0}function O(){E.value=!1}function k(){let{parentKey:t,tmNode:a}=e;a.disabled||l.value&&(i.value=t,r.value=null,n.value=a.key)}function A(){let{tmNode:t}=e;t.disabled||l.value&&n.value!==t.key&&k()}function j(t){if(e.tmNode.disabled||!l.value)return;let{relatedTarget:r}=t;r&&!f({target:r},`dropdownOption`)&&!f({target:r},`scrollbarRail`)&&(n.value=null)}function M(){let{value:n}=S,{tmNode:r}=e;l.value&&!n&&!r.disabled&&(t.doSelect(r.key,r.rawNode),t.doUpdateShow(!1))}return{labelField:p,renderLabel:u,renderIcon:d,siblingHasIcon:y.showIconRef,siblingHasSubmenu:y.hasSubmenuRef,menuProps:_,popoverBody:b,animated:c,mergedShowSubmenu:P(()=>w.value&&!T.value),rawNode:x,hasSubmenu:S,pending:L(()=>{let{value:t}=o,{key:n}=e.tmNode;return t.includes(n)}),childActive:L(()=>{let{value:t}=s,{key:n}=e.tmNode,r=t.findIndex(e=>n===e);return r!==-1&&r<t.length-1}),active:L(()=>{let{value:t}=s,{key:n}=e.tmNode,r=t.findIndex(e=>n===e);return r!==-1&&r===t.length-1}),mergedDisabled:C,renderOption:h,nodeProps:g,handleClick:M,handleMouseMove:A,handleMouseEnter:k,handleMouseLeave:j,handleSubmenuBeforeEnter:D,handleSubmenuAfterEnter:O}},render(){let{animated:e,rawNode:t,mergedShowSubmenu:n,clsPrefix:r,siblingHasIcon:a,siblingHasSubmenu:o,renderLabel:s,renderIcon:d,renderOption:f,nodeProps:p,props:m,scrollable:h}=this,g=null;if(n){let e=this.menuProps?.call(this,t,t.children);g=V(be,Object.assign({},e,{clsPrefix:r,scrollable:this.scrollable,tmNodes:this.tmNode.children,parentKey:this.tmNode.key}))}let _={class:[`${r}-dropdown-option-body`,this.pending&&`${r}-dropdown-option-body--pending`,this.active&&`${r}-dropdown-option-body--active`,this.childActive&&`${r}-dropdown-option-body--child-active`,this.mergedDisabled&&`${r}-dropdown-option-body--disabled`],onMousemove:this.handleMouseMove,onMouseenter:this.handleMouseEnter,onMouseleave:this.handleMouseLeave,onClick:this.handleClick},v=p?.(t),y=V(`div`,Object.assign({class:[`${r}-dropdown-option`,v?.class],"data-dropdown-option":!0},v),V(`div`,I(_,m),[V(`div`,{class:[`${r}-dropdown-option-body__prefix`,a&&`${r}-dropdown-option-body__prefix--show-icon`]},[d?d(t):i(t.icon)]),V(`div`,{"data-dropdown-option":!0,class:`${r}-dropdown-option-body__label`},s?s(t):i(t[this.labelField]??t.title)),V(`div`,{"data-dropdown-option":!0,class:[`${r}-dropdown-option-body__suffix`,o&&`${r}-dropdown-option-body__suffix--has-submenu`]},this.hasSubmenu?V(le,null,{default:()=>V(q,null)}):null)]),this.hasSubmenu?V(l,null,{default:()=>[V(u,null,{default:()=>V(`div`,{class:`${r}-dropdown-offset-container`},V(c,{show:this.mergedShowSubmenu,placement:this.placement,to:h&&this.popoverBody||void 0,teleportDisabled:!h},{default:()=>V(`div`,{class:`${r}-dropdown-menu-wrapper`},e?V(E,{onBeforeEnter:this.handleSubmenuBeforeEnter,onAfterEnter:this.handleSubmenuAfterEnter,name:`fade-in-scale-up-transition`,appear:!0},{default:()=>g}):g)}))})]}):null);return f?f({node:y,option:t}):y}});N();var ve=M({name:`NDropdownGroup`,props:{clsPrefix:{type:String,required:!0},tmNode:{type:Object,required:!0},parentKey:{type:[String,Number],default:null}},render(){let{tmNode:e,parentKey:t,clsPrefix:n}=this,{children:r}=e;return V(k,null,V(me,{clsPrefix:n,tmNode:e,key:e.key}),r?.map(e=>{let{rawNode:r}=e;return r.show===!1?null:ge(r)?V(pe,{clsPrefix:n,key:e.key}):e.isGroup?(h(`dropdown`,"`group` node is not allowed to be put in `group` node."),null):V($,{clsPrefix:n,tmNode:e,parentKey:t,key:e.key})}))}});N();var ye=M({name:`DropdownRenderOption`,props:{tmNode:{type:Object,required:!0}},render(){let{rawNode:{render:e,props:t}}=this.tmNode;return V(`div`,t,[e?.()])}});N(),y();var be=M({name:`DropdownMenu`,props:{scrollable:Boolean,showArrow:Boolean,arrowStyle:[String,Object],clsPrefix:{type:String,required:!0},tmNodes:{type:Array,default:()=>[]},parentKey:{type:[String,Number],default:null}},setup(t){let{renderIconRef:n,childrenFieldRef:r}=F(Z);H(X,{showIconRef:P(()=>{let e=n.value;return t.tmNodes.some(t=>{if(t.isGroup)return t.children?.some(({rawNode:t})=>e?e(t):t.icon);let{rawNode:n}=t;return e?e(n):n.icon})}),hasSubmenuRef:P(()=>{let{value:e}=r;return t.tmNodes.some(t=>{if(t.isGroup)return t.children?.some(({rawNode:t})=>Q(t,e));let{rawNode:n}=t;return Q(n,e)})})});let i=U(null);return H(e,null),H(d,null),H(a,i),{bodyRef:i}},render(){let{parentKey:e,clsPrefix:t,scrollable:n}=this,r=this.tmNodes.map(r=>{let{rawNode:i}=r;return i.show===!1?null:_e(i)?V(ye,{tmNode:r,key:r.key}):ge(i)?V(pe,{clsPrefix:t,key:r.key}):he(i)?V(ve,{clsPrefix:t,tmNode:r,parentKey:e,key:r.key}):V($,{clsPrefix:t,tmNode:r,parentKey:e,key:r.key,props:i.props,scrollable:n})});return V(`div`,{class:[`${t}-dropdown-menu`,n&&`${t}-dropdown-menu--scrollable`],ref:`bodyRef`},n?V(ae,{contentClass:`${t}-dropdown-menu__content`},{default:()=>r}):r,this.showArrow?s({clsPrefix:t,arrowStyle:this.arrowStyle,arrowClass:void 0,arrowWrapperClass:void 0,arrowWrapperStyle:void 0}):null)}}),xe=_(`dropdown-menu`,`
 transform-origin: var(--v-transform-origin);
 background-color: var(--n-color);
 border-radius: var(--n-border-radius);
 box-shadow: var(--n-box-shadow);
 position: relative;
 transition:
 background-color .3s var(--n-bezier),
 box-shadow .3s var(--n-bezier);
`,[ce(),_(`dropdown-option`,`
 position: relative;
 `,[v(`a`,`
 text-decoration: none;
 color: inherit;
 outline: none;
 `,[v(`&::before`,`
 content: "";
 position: absolute;
 left: 0;
 right: 0;
 top: 0;
 bottom: 0;
 `)]),_(`dropdown-option-body`,`
 display: flex;
 cursor: pointer;
 position: relative;
 height: var(--n-option-height);
 line-height: var(--n-option-height);
 font-size: var(--n-font-size);
 color: var(--n-option-text-color);
 transition: color .3s var(--n-bezier);
 `,[v(`&::before`,`
 content: "";
 position: absolute;
 top: 0;
 bottom: 0;
 left: 4px;
 right: 4px;
 transition: background-color .3s var(--n-bezier);
 border-radius: var(--n-border-radius);
 `),ee(`disabled`,[g(`pending`,`
 color: var(--n-option-text-color-hover);
 `,[z(`prefix, suffix`,`
 color: var(--n-option-text-color-hover);
 `),v(`&::before`,`background-color: var(--n-option-color-hover);`)]),g(`active`,`
 color: var(--n-option-text-color-active);
 `,[z(`prefix, suffix`,`
 color: var(--n-option-text-color-active);
 `),v(`&::before`,`background-color: var(--n-option-color-active);`)]),g(`child-active`,`
 color: var(--n-option-text-color-child-active);
 `,[z(`prefix, suffix`,`
 color: var(--n-option-text-color-child-active);
 `)])]),g(`disabled`,`
 cursor: not-allowed;
 opacity: var(--n-option-opacity-disabled);
 `),g(`group`,`
 font-size: calc(var(--n-font-size) - 1px);
 color: var(--n-group-header-text-color);
 `,[z(`prefix`,`
 width: calc(var(--n-option-prefix-width) / 2);
 `,[g(`show-icon`,`
 width: calc(var(--n-option-icon-prefix-width) / 2);
 `)])]),z(`prefix`,`
 width: var(--n-option-prefix-width);
 display: flex;
 justify-content: center;
 align-items: center;
 color: var(--n-prefix-color);
 transition: color .3s var(--n-bezier);
 z-index: 1;
 `,[g(`show-icon`,`
 width: var(--n-option-icon-prefix-width);
 `),_(`icon`,`
 font-size: var(--n-option-icon-size);
 `)]),z(`label`,`
 white-space: nowrap;
 flex: 1;
 z-index: 1;
 `),z(`suffix`,`
 box-sizing: border-box;
 flex-grow: 0;
 flex-shrink: 0;
 display: flex;
 justify-content: flex-end;
 align-items: center;
 min-width: var(--n-option-suffix-width);
 padding: 0 8px;
 transition: color .3s var(--n-bezier);
 color: var(--n-suffix-color);
 z-index: 1;
 `,[g(`has-submenu`,`
 width: var(--n-option-icon-suffix-width);
 `),_(`icon`,`
 font-size: var(--n-option-icon-size);
 `)]),_(`dropdown-menu`,`pointer-events: all;`)]),_(`dropdown-offset-container`,`
 pointer-events: none;
 position: absolute;
 left: 0;
 right: 0;
 top: -4px;
 bottom: -4px;
 `)]),_(`dropdown-divider`,`
 transition: background-color .3s var(--n-bezier);
 background-color: var(--n-divider-color);
 height: 1px;
 margin: 4px 0;
 `),_(`dropdown-menu-wrapper`,`
 transform-origin: var(--v-transform-origin);
 width: fit-content;
 `),v(`>`,[_(`scrollbar`,`
 height: inherit;
 max-height: inherit;
 `)]),ee(`scrollable`,`
 padding: var(--n-padding);
 `),g(`scrollable`,[z(`content`,`
 padding: var(--n-padding);
 `)])]);N(),y();var Se={animated:{type:Boolean,default:!0},keyboard:{type:Boolean,default:!0},size:{type:String,default:`medium`},inverted:Boolean,placement:{type:String,default:`bottom`},onSelect:[Function,Array],options:{type:Array,default:()=>[]},menuProps:Function,showArrow:Boolean,renderLabel:Function,renderIcon:Function,renderOption:Function,nodeProps:Function,labelField:{type:String,default:`label`},keyField:{type:String,default:`key`},childrenField:{type:String,default:`children`},value:[String,Number]},Ce=Object.keys(p),we=Object.assign(Object.assign(Object.assign({},p),Se),j.props),Te=M({name:`Dropdown`,inheritAttrs:!1,props:we,setup(e){let n=U(!1),r=oe(x(e,`show`),n),i=P(()=>{let{keyField:n,childrenField:r}=e;return t(e.options,{getKey(e){return e[n]},getDisabled(e){return e.disabled===!0},getIgnored(e){return e.type===`divider`||e.type===`render`},getChildren(e){return e[r]}})}),a=P(()=>i.value.treeNodes),o=U(null),s=U(null),c=U(null),l=P(()=>o.value??s.value??c.value??null),u=P(()=>i.value.getPath(l.value).keyPath),d=P(()=>i.value.getPath(e.value).keyPath),f=L(()=>e.keyboard&&r.value);K({keydown:{ArrowUp:{prevent:!0,handler:E},ArrowRight:{prevent:!0,handler:w},ArrowDown:{prevent:!0,handler:D},ArrowLeft:{prevent:!0,handler:S},Enter:{prevent:!0,handler:k},Escape:y}},f);let{mergedClsPrefixRef:p,inlineThemeDisabled:m}=b(e),h=j(`Dropdown`,`-dropdown`,xe,Y,e,p);H(Z,{labelFieldRef:x(e,`labelField`),childrenFieldRef:x(e,`childrenField`),renderLabelRef:x(e,`renderLabel`),renderIconRef:x(e,`renderIcon`),hoverKeyRef:o,keyboardKeyRef:s,lastToggledSubmenuKeyRef:c,pendingKeyPathRef:u,activeKeyPathRef:d,animatedRef:x(e,`animated`),mergedShowRef:r,nodePropsRef:x(e,`nodeProps`),renderOptionRef:x(e,`renderOption`),menuPropsRef:x(e,`menuProps`),doSelect:g,doUpdateShow:_}),R(r,t=>{!e.animated&&!t&&v()});function g(t,n){let{onSelect:r}=e;r&&O(r,t,n)}function _(t){let{"onUpdate:show":r,onUpdateShow:i}=e;r&&O(r,t),i&&O(i,t),n.value=t}function v(){o.value=null,s.value=null,c.value=null}function y(){_(!1)}function S(){M(`left`)}function w(){M(`right`)}function E(){M(`up`)}function D(){M(`down`)}function k(){let e=A();e?.isLeaf&&r.value&&(g(e.key,e.rawNode),_(!1))}function A(){let{value:e}=i,{value:t}=l;return!e||t===null?null:e.getNode(t)??null}function M(e){let{value:t}=l,{value:{getFirstAvailableNode:n}}=i,r=null;if(t===null){let e=n();e!==null&&(r=e.key)}else{let t=A();if(t){let n;switch(e){case`down`:n=t.getNext();break;case`up`:n=t.getPrev();break;case`right`:n=t.getChild();break;case`left`:n=t.getParent()}n&&(r=n.key)}}r!==null&&(o.value=null,s.value=r)}let N=P(()=>{let{size:t,inverted:n}=e,{common:{cubicBezierEaseInOut:r},self:i}=h.value,{padding:a,dividerColor:o,borderRadius:s,optionOpacityDisabled:c,[C(`optionIconSuffixWidth`,t)]:l,[C(`optionSuffixWidth`,t)]:u,[C(`optionIconPrefixWidth`,t)]:d,[C(`optionPrefixWidth`,t)]:f,[C(`fontSize`,t)]:p,[C(`optionHeight`,t)]:m,[C(`optionIconSize`,t)]:g}=i,_={"--n-bezier":r,"--n-font-size":p,"--n-padding":a,"--n-border-radius":s,"--n-option-height":m,"--n-option-prefix-width":f,"--n-option-icon-prefix-width":d,"--n-option-suffix-width":u,"--n-option-icon-suffix-width":l,"--n-option-icon-size":g,"--n-divider-color":o,"--n-option-opacity-disabled":c};return n?(_[`--n-color`]=i.colorInverted,_[`--n-option-color-hover`]=i.optionColorHoverInverted,_[`--n-option-color-active`]=i.optionColorActiveInverted,_[`--n-option-text-color`]=i.optionTextColorInverted,_[`--n-option-text-color-hover`]=i.optionTextColorHoverInverted,_[`--n-option-text-color-active`]=i.optionTextColorActiveInverted,_[`--n-option-text-color-child-active`]=i.optionTextColorChildActiveInverted,_[`--n-prefix-color`]=i.prefixColorInverted,_[`--n-suffix-color`]=i.suffixColorInverted,_[`--n-group-header-text-color`]=i.groupHeaderTextColorInverted):(_[`--n-color`]=i.color,_[`--n-option-color-hover`]=i.optionColorHover,_[`--n-option-color-active`]=i.optionColorActive,_[`--n-option-text-color`]=i.optionTextColor,_[`--n-option-text-color-hover`]=i.optionTextColorHover,_[`--n-option-text-color-active`]=i.optionTextColorActive,_[`--n-option-text-color-child-active`]=i.optionTextColorChildActive,_[`--n-prefix-color`]=i.prefixColor,_[`--n-suffix-color`]=i.suffixColor,_[`--n-group-header-text-color`]=i.groupHeaderTextColor),_}),F=m?T(`dropdown`,P(()=>`${e.size[0]}${e.inverted?`i`:``}`),N,e):void 0;return{mergedClsPrefix:p,mergedTheme:h,tmNodes:a,mergedShow:r,handleAfterLeave:()=>{e.animated&&v()},doUpdateShow:_,cssVars:m?void 0:N,themeClass:F?.themeClass,onRender:F?.onRender}},render(){let e=(e,t,n,r,i)=>{var a;let{mergedClsPrefix:o,menuProps:s}=this;(a=this.onRender)==null||a.call(this);let c=s?.(void 0,this.tmNodes.map(e=>e.rawNode))||{},l={ref:m(t),class:[e,`${o}-dropdown`,this.themeClass],clsPrefix:o,tmNodes:this.tmNodes,style:[...n,this.cssVars],showArrow:this.showArrow,arrowStyle:this.arrowStyle,scrollable:this.scrollable,onMouseenter:r,onMouseleave:i};return V(be,I(this.$attrs,l,c))},{mergedTheme:t}=this,r={show:this.mergedShow,theme:t.peers.Popover,themeOverrides:t.peerOverrides.Popover,internalOnAfterLeave:this.handleAfterLeave,internalRenderBody:e,onUpdateShow:this.doUpdateShow,"onUpdate:show":void 0};return V(n,Object.assign({},o(this.$props,Ce),r),{trigger:()=>{var e;return(e=this.$slots).default?.call(e)}})}});export{K as a,q as i,Y as n,J as r,Te as t};