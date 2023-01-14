/**
 * 随页面自适应可变长度的自定义滚动条
 * @param el 元素
 * @param options 参数
 * @constructor 构造函数
 */
function ScrollBar (el, options) {
  this.__el__ = el
  this.init()
}
ScrollBar.prototype = {
  constructor: ScrollBar,
  __elContent__: Object,
  __elScrollBox__: Object,
  __system__: Object,
  __maxHeight__: Object,
  __moveSize__: Object,
  __scale__: Object, // 滚动比例
  __totalScale__: Object, // 滚动比例总和
  __barStepMoveSize__: Object, // 滚动条每次移动高度
  __overflowHeight__: Object, // 内容溢出隐藏的高度
  __totalSize__: Number,
  __scaleBarHeight__: Number, // 滚动条高度比例
  __scrollbarHeight__: Number, // 滚动条高度
  init: function () {
    var _this = this
    // 每次移动20像素
    this.__moveSize__ = 20
    this.__totalSize__ = 0
    // 浏览器类型
    this.__system__ = window.navigator.userAgent.toLowerCase()
    // 内容显示区域高度
    this.__maxHeight__ = this.__el__.clientHeight
    // 内容元素
    if (this.__el__) {
      this.__elContent__ = this.__el__.firstElementChild
      // 溢出高度
      this.__overflowHeight__ = parseInt(this.__elContent__.clientHeight - this.__maxHeight__)
      if (this.__overflowHeight__ > 0) {
        // 内容页定位
        this.__elContent__.style.position = 'absolute'
        this.__elContent__.top = 0 + 'px'
        // 内容区域高度
        var heightContent = this.__elContent__.clientHeight
        var elScrollBox = this.__el__.getElementsByClassName('scroll-box')[0]
        if (!elScrollBox) {
          // 添加滚动条
          this.__elContent__.insertAdjacentHTML('afterend', '<div class="scroll-box"><div class="scrollbar"></div></div>')
        }
        // 获取滚动条容器
        this.__elScrollBox__ = this.__el__.lastElementChild
        this.__elScrollBox__.style.position = 'relative'
        // 获取滚动条
        this.__elScrollBar__ = this.__elScrollBox__.children[0]
        this.__elScrollBar__.style.position = 'absolute'
        this.__elScrollBar__.style.top = 0 + 'px'
        // 滚动条占页面高度比 = 溢出高度 / 内容页高度
        this.__scaleBarHeight__ = parseFloat(this.__overflowHeight__ / heightContent).toFixed(2)
        // 滚动条高度 = 显示内容区域高度页高度 * （1 - 滚动条高度比）+ ‘px'
        this.__scrollbarHeight__ = this.__maxHeight__ * (1 - this.__scaleBarHeight__) + 'px'
        // 根据溢出内容占比动态设置滚动条高度
        this.__elScrollBar__.style.height = this.__scrollbarHeight__
        // 移动比例
        this.__scale__ = parseFloat(this.__moveSize__ / this.__overflowHeight__).toFixed(2)
        // 移动比例总和
        this.__totalScale__ = 0
        this.__elScrollBox__ = this.__el__.getElementsByClassName('scroll-box')[0]
        this.__scrollbar__ = this.__el__.getElementsByClassName('scrollbar')[0]
        this.__scrollbar__.addEventListener('mousedown', function (ev) {
          ev = ev || event
          _this.moveBar(ev, _this)
        }, false)
        this.__scrollbar__.addEventListener('mouseup', function (ev) {
          ev = ev || event
          _this.stopMoveBar(ev, _this)
        }, false)
        this.mouseScroll()
      } else {
        this.__elContent__.style.width = 'calc(100%)'
      }
    }
  },
  /**
     * 鼠标滚轮事件
     */
  mouseScroll: function () {
    var that = this
    that.__elContent__.style.top = '0'
    // 获取bar移动的比率
    if (this.__system__.indexOf('firefox') !== -1) { // 火狐浏览器
      this.__el__.addEventListener('DOMMouseScroll', function (ev) {
        ev = ev || event
        if (ev.preventDefault()) {
          ev.preventDefault()
        } else {
          ev.returnValue = false
        }
        var top = that.getNumber(that.__elContent__.style.top)
        if (ev.detail < 0) { // 滚轮向上滚动
          // top值最大为0
          top = top + that.__moveSize__
          // top值最大为0
          if (top > 0) {
            top = 0
          }
        } else { // 滚轮向下滚动
          top = top - that.__moveSize__
          // 内容top值超出溢出高度，绝对值最大不能超过溢出高度
          if (top < -that.__overflowHeight__) {
            top = -that.__overflowHeight__
          }
        }
        that.__scale__ = parseFloat(Math.abs(top) / that.__overflowHeight__)
        that.__elContent__.style.top = top + 'px'
        that.__elScrollBar__.style.top = (that.__maxHeight__ - that.__scrollbar__.clientHeight) * that.__scale__ + 'px'
      }, false)
    } else { // 其他浏览器其他浏览器
      this.__el__.onmousewheel = function (ev) {
        ev = ev || event
        if (ev.preventDefault()) {
          ev.preventDefault()
        } else {
          ev.returnValue = false
        }
        var top = that.getNumber(that.__elContent__.style.top)
        if (ev.wheelDelta > 0) { // 滚轮向上
          top = top + that.__moveSize__
          // top值最大为0
          if (top > 0) {
            top = 0
          }
        } else { // 滚轮向下
          top = top - that.__moveSize__
          // 内容top值超出溢出高度，绝对值最大不能超过溢出高度
          if (top < -that.__overflowHeight__) {
            top = -that.__overflowHeight__
          }
        }
        that.__scale__ = parseFloat(Math.abs(top) / that.__overflowHeight__)
        that.__elContent__.style.top = top + 'px'
        that.__elScrollBar__.style.top = (that.__maxHeight__ - that.__scrollbar__.clientHeight) * that.__scale__ + 'px'
      }
    }
  },
  getNumber: function (num) {
    var index = num.indexOf('px')
    return parseInt(num.slice(0, index))
  },
  /**
     * 点击拖动滚动条
     */
  moveBar: function (ev, _this) {
    var target = ev.target
    var elOffsetTop = parseInt(_this.__el__.offsetTop)
    document.body.style.cursor = 'move'
    document.body.onmousemove = function (ev) {
      target.style.top = (parseInt(ev.pageY) - elOffsetTop) + 'px'
      var size = parseInt(target.style.top)
      var barMoveSize = _this.__maxHeight__ - _this.__elScrollBar__.clientHeight
      var scale = 0
      if ((size + _this.__elScrollBar__.clientHeight) > _this.__maxHeight__) {
        target.style.top = _this.__maxHeight__ - _this.__elScrollBar__.clientHeight + 'px'
        scale = 1
      } else {
        scale = parseFloat(size / barMoveSize).toFixed(2)
        if (scale < 0) {
          scale = 0
          target.style.top = 0 + 'px'
        }
      }
      _this.__elContent__.style.top = -_this.__overflowHeight__ * scale + 'px'
    }
    document.body.onmouseup = function () {
      document.body.style.cursor = ''
      document.body.onmousemove = null
      target.style.top = target.style.top + 'px'
    }
  },
  /**
     * 鼠标抬起拖拽停止
     * @param ev
     * @param _this
     */
  stopMoveBar: function (ev, _this) {
    document.body.style.cursor = 'move'
    document.body.onmousemove = null
  }
}
