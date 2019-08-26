var linkedSelect = function() {
    this.init.apply(this, arguments);
};
 
linkedSelect.prototype = {
 
    settings : {},
    elements : {},
    data     : {},
 
    init : function(settings) {
 
        settings = this.arrayMerge({
            actionTargetName  : '',
            changeTargetName  : '',
            stockFirstElement : true
        }, settings);
        this.settings = settings;
 
        this.data = {
            activeIndex : 0
        };
 
        if (settings.actionTargetName && settings.changeTargetName) {
            this.setup();
        }
 
        return this;
    },
 
    setup : function() {
        var actionTargetElements = document.getElementsByName(this.settings.actionTargetName);
        var changeTargetElements = document.getElementsByName(this.settings.changeTargetName);
 
        if (actionTargetElements.length > 0 && changeTargetElements.length > 0) {
            var actionTargetElement = actionTargetElements[0],
                changeTargetElement = changeTargetElements[0],
                optionGroups        = changeTargetElement.getElementsByTagName('optgroup'),
                optionElementObjs   = [],
                optionElements      = [],
                optionCloneElements = [],
                attrNode            = null,
                selectIndex         = 0,
                ii                  = 0,
                len2                = 0;
 
            for (var i = 0, len = optionGroups.length; i < len; i++) {
                optionElements          = optionGroups[i].getElementsByTagName('option');
                optionCloneElements     = [];
                selectIndex             = 0;
 
                for (ii = 0, len2 = optionElements.length; ii < len2; ii++) {
                    attrNode = optionElements[ii].getAttributeNode('selected');
                    if (attrNode) {
                        selectIndex = ii;
                        optionElements[ii].removeAttribute(attrNode);
                    }
                    optionCloneElements.push(optionElements[ii].cloneNode(true));
                }
 
                optionElementObjs.push({
                    label       : optionGroups[i].label,
                    elements    : optionCloneElements,
                    selectIndex : selectIndex
                });
 
            }
 
            var stockFirstElement = changeTargetElement.getElementsByTagName('option')[0].cloneNode(true);
            changeTargetElement.innerHTML = '';
 
            if (this.settings.stockFirstElement) {
                changeTargetElement.appendChild(stockFirstElement);
            }
 
            this.elements = {
                changeTargetElement : changeTargetElement,
                actionTargetElement : actionTargetElement,
                optionElements      : optionElementObjs
            };
 
            this.addEvent();
            this.changeOption();
        }
 
        return this;
    },
 
    addEvent : function() {
        var self = this;
        if (this.elements.actionTargetElement) {
            this.elements.actionTargetElement.onchange = function() {
                self.changeOption();
            };
        }
        return this;
    },
 
    changeOption : function() {
        if (this.elements.actionTargetElement && this.elements.changeTargetElement && this.elements.optionElements) {
            var actionTargetElement     = this.elements.actionTargetElement,
                changeTargetElement     = this.elements.changeTargetElement,
                optionElements          = this.elements.optionElements,
                oldSelectIndex          = this.data.activeIndex,
                selectIndex             = actionTargetElement.selectedIndex,
                changeTargetSelectIndex = changeTargetElement.selectedIndex,
                options                 = actionTargetElement.options,
                selectValue             = options[selectIndex].value,
                setSelected             = 0,
                oldOptionElements       = null,
                stockFirstElement       = '',
                setOptions              = [],
                i                       = 0,
                len                     = 0;
 
            this.data.activeIndex = selectIndex;
 
            if (this.settings.stockFirstElement) {
                stockFirstElement = changeTargetElement.getElementsByTagName('option')[0].cloneNode(true);
                changeTargetElement.innerHTML = '';
                changeTargetElement.appendChild(stockFirstElement);
                oldSelectIndex--;
            } else {
                changeTargetElement.innerHTML = '';
            }
 
            if (oldSelectIndex > -1) {
                oldOptionElements = optionElements[oldSelectIndex];
                oldOptionElements.selectIndex = changeTargetSelectIndex;
            }
 
            if (selectValue !== '') {
                for (i = 0, len = optionElements.length; i < len; i++) {
                    if (optionElements[i].label === selectValue) {
                        setSelected = optionElements[i].selectIndex;
                        setOptions  = optionElements[i].elements;
                        break;
                    }
                }
 
                for (i = 0, len = setOptions.length; i < len; i++) {
                    changeTargetElement.appendChild(setOptions[i]);
                }
                changeTargetElement.selectedIndex = setSelected;
            }
        }
        return this;
    },

    arrayMerge : function() {
        var key, result = false;
        if (arguments && arguments.length > 0) {
            result = {};
            for (var i = 0, len = arguments.length;i < len; i++) {
                if (arguments[i] && typeof arguments[i] === 'object') {
                    for (key in arguments[i]) {
                        if (isFinite(key)) {
                            result.push(arguments[i][key]);
                        } else {
                            result[key] = arguments[i][key];
                        }
                    }
                }
            }
        }
        return result;
    }
};
